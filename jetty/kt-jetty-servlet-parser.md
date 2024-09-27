Jetty를 사용하여 폴더를 탐색하고 파일 유형에 따라 각기 다른 파서를 호출하는 서블릿을 구현할 수 있습니다. 다음은 이를 구현하는 방법입니다.

1. **폴더 탐색**: Java의 `Files.walk()` 메서드를 사용하여 폴더 내의 파일을 순회합니다.
2. **파일 유형 확인**: 파일 확장자를 확인하여 Java 또는 Kotlin 파일인지 판별합니다.
3. **파서 호출**: 파일 유형에 따라 적절한 파서를 호출합니다. Java 파일인 경우 Java 파서를 호출하고, Kotlin 파일인 경우 Kotlin 파서를 호출합니다.

다음은 기본적인 Jetty 서블릿 예시입니다:

```kotlin
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletHandler
import java.nio.file.Files
import java.nio.file.Paths

class FolderParserServlet : javax.servlet.http.HttpServlet() {
    override fun doGet(req: javax.servlet.http.HttpServletRequest, resp: javax.servlet.http.HttpServletResponse) {
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

        // 폴더 내 파일 탐색
        Files.walk(folder).forEach { path ->
            if (Files.isRegularFile(path)) {
                val fileName = path.fileName.toString()
                when {
                    fileName.endsWith(".java") -> {
                        // Java 파서 호출 (구현 필요)
                        parseJavaFile(path.toString())
                    }
                    fileName.endsWith(".kt") -> {
                        // Kotlin 파서 호출 (구현 필요)
                        parseKotlinFile(path.toString())
                    }
                }
            }
        }
        
        resp.writer.write("Parsing completed")
    }

    private fun parseJavaFile(filePath: String) {
        // Java 파싱 로직 구현
        println("Parsing Java file: $filePath")
    }

    private fun parseKotlinFile(filePath: String) {
        // Kotlin 파싱 로직 구현
        println("Parsing Kotlin file: $filePath")
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

### 주요 흐름:
1. **`doGet` 메서드**: 클라이언트 요청을 처리하고 `folderPath`를 매개변수로 받아 폴더 내 파일을 탐색합니다.
2. **파일 확장자 확인**: `.java` 또는 `.kt` 확장자를 기준으로 각기 다른 파서를 호출합니다.
3. **파서 메서드**: `parseJavaFile()` 및 `parseKotlinFile()` 메서드는 각각의 파일에 대해 파싱을 수행하는 로직을 포함합니다. 이 부분에 실제 파서 구현을 추가할 수 있습니다.

이 코드를 실행하면 `GET` 요청을 통해 `folderPath`를 입력받고, 해당 폴더 내의 파일을 확인하여 파싱 작업을 수행하게 됩니다.