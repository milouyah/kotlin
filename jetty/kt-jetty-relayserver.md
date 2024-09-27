Jetty와 Kotlin을 사용해 relay 서버를 구축하는 것은 가능하며, 이를 위해 HTTP 요청을 다른 내부 서버로 전달한 후 응답을 반환하는 방식으로 구현할 수 있습니다. 이를 구현하는 기본적인 흐름은 다음과 같습니다.

1. **Jetty 서버 설정**: Jetty를 사용하여 기본 HTTP 서버를 설정.
2. **Relay 기능 구현**: 들어온 요청을 내부의 다른 서버로 전달하고, 해당 서버로부터 받은 응답을 클라이언트로 다시 반환.

아래는 Kotlin과 Jetty를 사용하여 간단한 relay 서버를 구현하는 예시입니다.

### 1. `build.gradle.kts`에 의존성 추가

```kotlin
dependencies {
    implementation("org.eclipse.jetty:jetty-server:11.0.15")
    implementation("org.eclipse.jetty:jetty-servlet:11.0.15")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.2")
}
```

### 2. Kotlin으로 Jetty Relay 서버 구현

```kotlin
import org.apache.hc.core5.http.io.entity.EntityUtils
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.core5.http.ClassicHttpResponse
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletHandler
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RelayServlet : HttpServlet() {
    private val client: CloseableHttpClient = HttpClients.createDefault()

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val targetUrl = "http://internal-server:8080${req.requestURI}"  // 내부 서버 주소 설정

        // 내부 서버로 요청 전달
        val httpGet = ClassicRequestBuilder.get(targetUrl).build()

        client.execute(httpGet) { response: ClassicHttpResponse ->
            // 내부 서버로부터 받은 응답을 클라이언트에 전달
            resp.status = response.code
            resp.writer.write(EntityUtils.toString(response.entity))
        }
    }

    // 필요시 doPost, doPut 등도 추가 구현 가능
}

fun main() {
    val server = Server(8080)  // Jetty 서버 설정
    val handler = ServletHandler()
    server.handler = handler

    handler.addServletWithMapping(RelayServlet::class.java, "/*")

    server.start()
    server.join()
}
```

### 설명:
1. **Jetty 서버**는 8080 포트에서 요청을 수신합니다.
2. `RelayServlet`은 들어오는 요청을 내부 서버(`http://internal-server:8080`)로 다시 전달하고, 해당 응답을 받아 클라이언트로 그대로 반환합니다.
3. **Apache HttpClient** 라이브러리를 사용해 요청을 내부 서버로 보냅니다.

이 코드를 사용하면 기본적인 relay 서버를 만들 수 있으며, 필요에 따라 `POST`, `PUT` 등 다른 HTTP 메서드에 대한 지원도 추가할 수 있습니다.