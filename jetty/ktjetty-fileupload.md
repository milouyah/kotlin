Kotlin과 Jetty를 사용하여 ZIP 파일을 업로드하고 이를 target 폴더에 풀어 JSON 형식으로 응답하는 예제를 작성하려면 다음 단계를 따를 수 있습니다.

1. Maven 또는 Gradle 의존성 추가

Jetty와 파일 처리 관련 의존성을 추가해야 합니다. 여기서는 jetty-servlet, kotlinx.serialization, 그리고 ZIP 파일을 처리하기 위한 Java의 기본 패키지를 사용합니다.

Gradle 예시

implementation("org.eclipse.jetty:jetty-servlet:11.0.12")
implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

2. 서블릿 작성

ZIP 파일을 받으면 target 폴더에 압축을 해제하고, 해당 폴더의 파일 목록을 JSON으로 변환하여 응답하는 서블릿을 작성합니다.
```kotlin
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletHandler
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import javax.servlet.MultipartConfigElement
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Serializable
data class FileListResponse(val files: List<String>)

class UploadServlet : HttpServlet() {

    @Throws(ServletException::class)
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        // Multipart Config 설정
        req.setAttribute("org.eclipse.jetty.multipartConfig", MultipartConfigElement("/tmp"))

        val targetDir = File("target")
        if (!targetDir.exists()) {
            targetDir.mkdirs()
        }

        // 업로드된 파일 스트림 읽기
        val zipFilePart = req.getPart("file")
        val zipInputStream = ZipInputStream(zipFilePart.inputStream)
        unzipToFolder(zipInputStream, targetDir)

        // target 폴더의 파일 목록을 JSON으로 변환
        val fileList = targetDir.listFiles()?.map { it.name } ?: emptyList()
        val jsonResponse = Json.encodeToString(FileListResponse(fileList))

        resp.contentType = "application/json"
        resp.writer.write(jsonResponse)
    }

    private fun unzipToFolder(zipInputStream: ZipInputStream, targetDir: File) {
        var entry: ZipEntry? = zipInputStream.nextEntry
        while (entry != null) {
            val newFile = File(targetDir, entry.name)
            if (entry.isDirectory) {
                newFile.mkdirs()
            } else {
                // 파일을 target 폴더에 저장
                FileOutputStream(newFile).use { output ->
                    zipInputStream.copyTo(output)
                }
            }
            zipInputStream.closeEntry()
            entry = zipInputStream.nextEntry
        }
    }
}

fun main() {
    val server = Server(8080)
    val handler = ServletHandler()
    server.handler = handler

    handler.addServletWithMapping(UploadServlet::class.java, "/upload")

    server.start()
    server.join()
}
```


3. 서버 설명

UploadServlet은 /upload 경로에서 POST 요청을 처리합니다.

사용자가 file이라는 이름으로 업로드한 ZIP 파일을 수신하고, target 폴더에 압축을 해제합니다.

압축 해제된 파일 목록을 JSON으로 변환하여 응답합니다.


4. 요청 예시

curl을 사용하여 ZIP 파일을 업로드할 수 있습니다:

curl -X POST -F "file=@yourfile.zip" http://localhost:8080/upload

5. 응답 예시

서버는 다음과 같은 JSON 응답을 반환합니다:

{
  "files": ["file1.txt", "folder/file2.txt", "image.png"]
}

이 코드는 기본적인 기능을 구현하며, 파일 확장자 검사, 오류 처리, 보안 개선 등을 추가하여 더욱 완성도 높은 코드를 작성할 수 있습니다.

