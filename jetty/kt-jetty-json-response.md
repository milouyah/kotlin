Jetty 서블릿에서 JSON 형식으로 리포트를 반환하려면, Kotlin에서 JSON 라이브러리(예: `kotlinx.serialization` 또는 `Gson`)를 사용하여 데이터를 JSON 형식으로 변환할 수 있습니다.

다음은 `kotlinx.serialization`을 사용하여 JSON 형식의 리포트를 반환하는 방법입니다.

### Gradle 의존성 추가

먼저 `kotlinx.serialization`을 사용하기 위해 Gradle에 의존성을 추가합니다.

```kotlin
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
}
```

### 코드 수정

`kotlinx.serialization`을 사용하여 파일의 이름과 파싱 결과를 JSON으로 변환한 후, HTTP 응답으로 반환하는 방식으로 수정할 수 있습니다.

```kotlin
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletHandler
import java.nio.file.Files
import java.nio.file.Paths
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

// JSON으로 반환할 데이터 클래스
@Serializable
data class FileParseReport(val fileName: String, val status: String, val message: String)

class FolderParserServlet : javax.servlet.http.HttpServlet() {
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val folderPath = req.getParameter("folderPath")
        
        if (folderPath == null || folderPath.isEmpty()) {
            resp.writer.write("folderPath is required")
            return
        }
        
        val folder = Paths.get(folderPath)
        if (!Files.exists(folder) || !Files.isDirectory(folder)) {
            resp.writer.write("Invalid folder path")
            return
        }

        val reports = mutableListOf<FileParseReport>()

        // 폴더 내 파일 탐색
        Files.walk(folder).forEach { path ->
            if (Files.isRegularFile(path)) {
                val fileName = path.fileName.toString()
                when {
                    fileName.endsWith(".java") -> {
                        // Java 파서 호출 (구현 필요)
                        reports.add(parseJavaFile(path.toString()))
                    }
                    fileName.endsWith(".kt") -> {
                        // Kotlin 파서 호출 (구현 필요)
                        reports.add(parseKotlinFile(path.toString()))
                    }
                }
            }
        }

        // JSON 리포트 작성
        resp.contentType = "application/json"
        resp.characterEncoding = "UTF-8"
        val jsonResponse = Json.encodeToString(reports)
        resp.writer.write(jsonResponse)
    }

    private fun parseJavaFile(filePath: String): FileParseReport {
        // Java 파싱 로직 구현 (예시)
        println("Parsing Java file: $filePath")
        return FileParseReport(fileName = filePath, status = "success", message = "Java file parsed successfully")
    }

    private fun parseKotlinFile(filePath: String): FileParseReport {
        // Kotlin 파싱 로직 구현 (예시)
        println("Parsing Kotlin file: $filePath")
        return FileParseReport(fileName = filePath, status = "success", message = "Kotlin file parsed successfully")
    }
}

fun main() {
    val server = Server(8080)
    val handler = ServletHandler()
    server.handler = handler
    handler.addServletWithMapping(FolderParserServlet::class.java, "/parse")
    server.start()
    server.join()
}
```

### 주요 수정 사항:
1. **`FileParseReport` 데이터 클래스**: 파일 이름, 상태, 메시지를 담고 있는 데이터 클래스를 JSON으로 변환하여 반환합니다.
2. **파싱 메서드 수정**: 각 파서 메서드(`parseJavaFile` 및 `parseKotlinFile`)는 `FileParseReport` 객체를 반환하여 파싱 결과를 나타냅니다.
3. **`kotlinx.serialization`**: JSON으로 변환하기 위해 `Json.encodeToString(reports)`를 사용하여 리스트를 JSON으로 변환합니다.
4. **응답 설정**: 응답의 Content-Type을 `application/json`으로 설정하고, UTF-8로 인코딩하여 클라이언트에 JSON 응답을 전달합니다.

### 응답 예시:

```json
[
  {
    "fileName": "/path/to/file.java",
    "status": "success",
    "message": "Java file parsed successfully"
  },
  {
    "fileName": "/path/to/file.kt",
    "status": "success",
    "message": "Kotlin file parsed successfully"
  }
]
```

이 코드로, 폴더 내의 파일을 파싱한 후 각 파일의 이름과 파싱 결과를 JSON 형식으로 반환하는 서블릿을 구현할 수 있습니다.