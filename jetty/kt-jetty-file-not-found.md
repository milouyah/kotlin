Jetty에서 File Not Found 상황에 대한 응답을 처리할 때는, HttpServletResponse 객체를 이용하여 적절한 HTTP 상태 코드와 함께 응답을 반환하는 것이 좋습니다. 아래와 같이 doGet() 메소드에서 파일이 없는 경우 404 Not Found 상태 코드를 설정할 수 있습니다:

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.File

class FileServlet : HttpServlet() {
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val filePath = "/path/to/your/file.txt"
        val file = File(filePath)

        if (!file.exists()) {
            // 파일이 존재하지 않으면 404 상태 코드 설정
            resp.status = HttpServletResponse.SC_NOT_FOUND
            resp.writer.write("File not found: $filePath")
            return
        }

        // 파일이 존재할 경우 파일 내용을 읽어 응답으로 보냄
        resp.contentType = "text/plain"
        file.inputStream().use { input ->
            resp.outputStream.use { output ->
                input.copyTo(output)
            }
        }
    }
}

위 코드에서, 파일이 존재하지 않을 경우 HttpServletResponse.SC_NOT_FOUND를 상태 코드로 설정하고 적절한 메시지를 작성하여 응답합니다.

resp.status = HttpServletResponse.SC_NOT_FOUND를 사용하여 상태 코드를 404로 설정합니다.

resp.writer.write()를 사용하여 사용자에게 에러 메시지를 제공합니다.


이렇게 하면 클라이언트는 파일이 존재하지 않는 경우 적절한 에러 응답을 받을 수 있습니다.

