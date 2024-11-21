# Ktor Caching

Ktor에서 **캐싱(Caching)** 은 클라이언트와 서버 간 데이터 요청을 최적화하고 네트워크 사용량을 줄이기 위해 중요한 역할을 합니다. Ktor는 HTTP 캐싱 메커니즘을 활용하여 캐싱 헤더를 설정하거나, 특정 데이터를 서버 쪽에서 캐싱할 수 있는 여러 방법을 제공합니다.


---

## 1. HTTP 캐싱 헤더 설정

HTTP 프로토콜이 제공하는 Cache-Control, ETag, Last-Modified 같은 헤더를 사용하여 캐싱을 구현할 수 있습니다.

예제: Cache-Control 헤더 설정

```
install(CachingHeaders)

routing {
    get("/data") {
        call.response.cacheControl(CacheControl.MaxAge(maxAgeSeconds = 60))
        call.respondText("This is cacheable data.")
    }
}
```

설명

CacheControl.MaxAge는 클라이언트가 데이터를 60초 동안 캐싱할 수 있도록 설정합니다.

클라이언트가 설정된 시간 동안 동일한 데이터를 다시 요청하지 않도록 합니다.



---

## 2. ETag를 사용한 캐싱

ETag는 데이터의 고유 식별자를 클라이언트에 제공하여 데이터 변경 여부를 확인하고 필요 시에만 갱신을 수행합니다.

예제: ETag 사용

```
routing {
    get("/data") {
        val data = "Hello, Cache!"
        val etag = "\"${data.hashCode()}\""
        call.response.etag(etag)
        
        if (call.request.etag() == etag) {
            call.respond(HttpStatusCode.NotModified)
        } else {
            call.respondText(data)
        }
    }
}
```

설명

서버는 데이터의 해시값을 ETag로 설정하여 클라이언트가 이를 저장하게 합니다.

클라이언트는 이후 요청 시 저장된 ETag를 서버로 전송하며, 서버가 동일한 ETag를 확인하면 304 Not Modified를 반환하여 데이터를 재전송하지 않습니다.



---

## 3. 서버 측 캐싱

서버에서 자주 사용되는 데이터를 메모리나 외부 캐싱 시스템(Redis 등)에 저장하여 처리 속도를 높일 수 있습니다.

메모리 기반 캐싱 예제

```
val cache = mutableMapOf<String, String>()

routing {
    get("/cached-data") {
        val key = "myData"
        val cachedData = cache[key]
        
        if (cachedData != null) {
            call.respondText("Cached: $cachedData")
        } else {
            val newData = "This is new data"
            cache[key] = newData
            call.respondText("Generated: $newData")
        }
    }
}
```

설명

요청된 데이터를 메모리에 저장하고, 이후 동일한 요청이 들어오면 저장된 데이터를 반환합니다.

주의: 메모리 캐싱은 서버 재시작 시 데이터가 사라질 수 있으며, 큰 데이터는 메모리 부족 문제를 야기할 수 있습니다.



---

## 4. 외부 캐싱 시스템 사용

Redis나 Memcached 같은 외부 캐싱 시스템을 사용하면 더 안정적이고 확장 가능한 캐싱을 구현할 수 있습니다.

Redis와 Ktor를 연동한 캐싱

```
import io.lettuce.core.RedisClient
import io.lettuce.core.api.sync.RedisCommands

val redisClient = RedisClient.create("redis://localhost:6379")
val redisCommands: RedisCommands<String, String> = redisClient.connect().sync()

routing {
    get("/redis-cached") {
        val key = "myData"
        val cachedData = redisCommands.get(key)
        
        if (cachedData != null) {
            call.respondText("Cached from Redis: $cachedData")
        } else {
            val newData = "This is new Redis data"
            redisCommands.set(key, newData)
            call.respondText("Generated and cached: $newData")
        }
    }
}
```

설명

Redis를 사용하여 데이터를 캐싱하고, 요청마다 캐싱된 데이터를 반환합니다.

Redis는 지속성과 확장성을 제공하며, 대규모 애플리케이션에서 많이 사용됩니다.



---

## 5. Static 파일 캐싱

정적 파일은 Static 플러그인을 통해 캐싱 가능합니다.

정적 파일 캐싱 예제

```
install(StaticFiles) {
    static("/static") {
        files("resources/static")
        defaultResource("index.html")
    }
}

routing {
    get("/") {
        call.response.cacheControl(CacheControl.MaxAge(maxAgeSeconds = 3600))
        call.respondRedirect("/static/index.html")
    }
}
```

---

## 6. 플러그인 사용 (외부 라이브러리)

외부 라이브러리(예: Caffeine, Guava)를 활용하면 더 세부적인 캐싱 전략을 구현할 수 있습니다.
예를 들어, Caffeine은 JVM 기반의 고성능 캐싱 라이브러리입니다.

Caffeine 캐싱 예제

```
val cache = Caffeine.newBuilder()
    .expireAfterWrite(10, TimeUnit.MINUTES)
    .maximumSize(100)
    .build<String, String>()

routing {
    get("/caffeine-cached") {
        val key = "myData"
        val cachedData = cache.getIfPresent(key)
        
        if (cachedData != null) {
            call.respondText("Cached with Caffeine: $cachedData")
        } else {
            val newData = "Generated data"
            cache.put(key, newData)
            call.respondText("Generated and cached: $newData")
        }
    }
}
```

---

결론

Ktor에서 캐싱은 HTTP 캐싱 헤더, 서버 측 캐싱, 또는 외부 캐싱 시스템을 사용하여 구현할 수 있습니다.
간단한 캐싱은 HTTP 헤더를 활용하고, 복잡한 캐싱이 필요하다면 Redis나 Caffeine 같은 솔루션을 선택하는 것이 좋습니다.


# Client

Ktor에서 클라이언트 캐싱은 HTTP 요청/응답 데이터의 재사용을 통해 네트워크 요청을 최적화하고 성능을 향상시키기 위해 활용됩니다. Ktor 클라이언트는 별도의 캐싱 플러그인을 제공하지는 않지만, 캐시 구현을 위해 다음과 같은 방법을 사용할 수 있습니다.


---

1. Ktor 클라이언트의 캐시를 직접 구현

Ktor 클라이언트는 HTTP 요청을 수행하는 도구로, 데이터를 저장하고 재사용할 수 있도록 캐시 레이어를 추가해야 합니다.

예제: 메모리 기반 캐싱

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.*

val cache = mutableMapOf<String, String>() // 메모리 캐시

val client = HttpClient()

suspend fun fetchData(url: String): String {
    // 캐시 확인
    cache[url]?.let { cachedResponse ->
        return "Cached: $cachedResponse"
    }
    
    // 서버에서 데이터 가져오기
    val response = client.get(url)
    val responseBody = response.bodyAsText()

    // 캐싱 저장
    cache[url] = responseBody

    return "Fetched: $responseBody"
}

fun main() = runBlocking {
    println(fetchData("https://api.example.com/data")) // 서버 요청
    println(fetchData("https://api.example.com/data")) // 캐시 반환
}

설명

첫 번째 요청 시 서버에서 데이터를 가져옵니다.

가져온 데이터를 cache에 저장하여 동일한 요청에 대해 캐시 데이터를 반환합니다.



---

2. ETag 기반 캐싱

서버가 ETag를 제공하는 경우, 클라이언트가 이를 활용해 데이터를 조건부로 가져올 수 있습니다.

예제: ETag 사용

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

val client = HttpClient()

var etagCache: String? = null

suspend fun fetchDataWithETag(url: String): String {
    val response = client.get(url) {
        // 저장된 ETag를 조건부 요청 헤더에 추가
        etagCache?.let {
            header("If-None-Match", it)
        }
    }

    return if (response.status.value == 304) {
        "Cached (Not Modified)"
    } else {
        // ETag 업데이트
        etagCache = response.headers["ETag"]
        "Fetched: ${response.bodyAsText()}"
    }
}

fun main() = runBlocking {
    println(fetchDataWithETag("https://api.example.com/data")) // 서버 요청
    println(fetchDataWithETag("https://api.example.com/data")) // 캐시 반환 (304)
}

설명

서버가 ETag를 제공하면, 클라이언트는 If-None-Match 헤더를 통해 데이터 변경 여부를 확인할 수 있습니다.

서버 응답이 304 Not Modified라면, 데이터를 다시 다운로드하지 않고 기존 데이터를 재사용합니다.



---

3. 파일 기반 캐싱

캐시를 파일에 저장하면 앱이 재시작되더라도 데이터를 재사용할 수 있습니다.

예제: 파일 캐싱

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.*
import java.io.File

val client = HttpClient()

suspend fun fetchDataWithFileCache(url: String, cacheFile: File): String {
    if (cacheFile.exists()) {
        // 캐시된 파일 읽기
        return "Cached: ${cacheFile.readText()}"
    }

    // 서버에서 데이터 가져오기
    val response = client.get(url)
    val responseBody = response.bodyAsText()

    // 파일에 저장
    cacheFile.writeText(responseBody)

    return "Fetched: $responseBody"
}

fun main() = runBlocking {
    val cacheFile = File("cache.txt")

    println(fetchDataWithFileCache("https://api.example.com/data", cacheFile)) // 서버 요청
    println(fetchDataWithFileCache("https://api.example.com/data", cacheFile)) // 파일 캐시 반환
}

설명

데이터를 파일에 저장하여 요청을 재사용합니다.

네트워크 요청이 불필요한 경우, 캐시 파일에서 데이터를 읽습니다.



---

4. 외부 캐싱 라이브러리 사용

캐싱 전략이 복잡하거나 고성능이 요구되는 경우, 외부 라이브러리를 Ktor 클라이언트와 통합할 수 있습니다. 예를 들어, Caffeine이나 Redis를 사용할 수 있습니다.

예제: Caffeine과 연동

import com.github.benmanes.caffeine.cache.Caffeine
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

val cache = Caffeine.newBuilder()
    .expireAfterWrite(10, TimeUnit.MINUTES)
    .maximumSize(100)
    .build<String, String>()

val client = HttpClient()

suspend fun fetchDataWithCaffeine(url: String): String {
    return cache.get(url) {
        runBlocking {
            // 캐시에 없는 경우 서버 요청
            client.get(url).bodyAsText()
        }
    }
}

fun main() = runBlocking {
    println(fetchDataWithCaffeine("https://api.example.com/data")) // 서버 요청
    println(fetchDataWithCaffeine("https://api.example.com/data")) // 캐시 반환
}


---

5. Ktor 클라이언트 플러그인 사용

클라이언트에서 요청/응답 데이터를 가로채 캐시를 처리할 수 있는 플러그인을 직접 구현할 수도 있습니다.

플러그인 예제

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

val client = HttpClient {
    install(HttpResponseValidator) {
        validateResponse { response ->
            val url = response.request.url.toString()
            val body = response.bodyAsText()
            
            // 캐싱 로직 추가
            cache[url] = body
        }
    }
}

suspend fun fetchWithPlugin(url: String): String {
    return cache[url] ?: client.get(url).bodyAsText()
}

설명

클라이언트의 플러그인 기능을 활용해 요청/응답 시 캐싱 로직을 주입할 수 있습니다.



---

6. 로컬 데이터 저장소 활용 (ex: SharedPreferences, Database)

Ktor 클라이언트와 상관없이, 응답 데이터를 로컬 데이터 저장소에 저장하여 캐싱할 수 있습니다. 이를 통해 장기적인 데이터 보존도 가능합니다.


---

결론

Ktor 클라이언트에서 캐싱은 기본적으로 메모리, 파일, 또는 외부 라이브러리를 활용해 구현해야 합니다.

간단한 캐싱 → 메모리 기반 캐싱

데이터 유지 필요 → 파일 기반 캐싱

복잡한 캐싱 요구 → Caffeine 또는 Redis 연동


요구사항에 따라 적절한 캐싱 방법을 선택하세요!


