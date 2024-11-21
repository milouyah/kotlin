Ktor에서 **캐싱(Caching)**은 클라이언트와 서버 간 데이터 요청을 최적화하고 네트워크 사용량을 줄이기 위해 중요한 역할을 합니다. Ktor는 HTTP 캐싱 메커니즘을 활용하여 캐싱 헤더를 설정하거나, 특정 데이터를 서버 쪽에서 캐싱할 수 있는 여러 방법을 제공합니다.


---

1. HTTP 캐싱 헤더 설정

HTTP 프로토콜이 제공하는 Cache-Control, ETag, Last-Modified 같은 헤더를 사용하여 캐싱을 구현할 수 있습니다.

예제: Cache-Control 헤더 설정

install(CachingHeaders)

routing {
    get("/data") {
        call.response.cacheControl(CacheControl.MaxAge(maxAgeSeconds = 60))
        call.respondText("This is cacheable data.")
    }
}

설명

CacheControl.MaxAge는 클라이언트가 데이터를 60초 동안 캐싱할 수 있도록 설정합니다.

클라이언트가 설정된 시간 동안 동일한 데이터를 다시 요청하지 않도록 합니다.



---

2. ETag를 사용한 캐싱

ETag는 데이터의 고유 식별자를 클라이언트에 제공하여 데이터 변경 여부를 확인하고 필요 시에만 갱신을 수행합니다.

예제: ETag 사용

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

설명

서버는 데이터의 해시값을 ETag로 설정하여 클라이언트가 이를 저장하게 합니다.

클라이언트는 이후 요청 시 저장된 ETag를 서버로 전송하며, 서버가 동일한 ETag를 확인하면 304 Not Modified를 반환하여 데이터를 재전송하지 않습니다.



---

3. 서버 측 캐싱

서버에서 자주 사용되는 데이터를 메모리나 외부 캐싱 시스템(Redis 등)에 저장하여 처리 속도를 높일 수 있습니다.

메모리 기반 캐싱 예제

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

설명

요청된 데이터를 메모리에 저장하고, 이후 동일한 요청이 들어오면 저장된 데이터를 반환합니다.

주의: 메모리 캐싱은 서버 재시작 시 데이터가 사라질 수 있으며, 큰 데이터는 메모리 부족 문제를 야기할 수 있습니다.



---

4. 외부 캐싱 시스템 사용

Redis나 Memcached 같은 외부 캐싱 시스템을 사용하면 더 안정적이고 확장 가능한 캐싱을 구현할 수 있습니다.

Redis와 Ktor를 연동한 캐싱

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

설명

Redis를 사용하여 데이터를 캐싱하고, 요청마다 캐싱된 데이터를 반환합니다.

Redis는 지속성과 확장성을 제공하며, 대규모 애플리케이션에서 많이 사용됩니다.



---

5. Static 파일 캐싱

정적 파일은 Static 플러그인을 통해 캐싱 가능합니다.

정적 파일 캐싱 예제

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


---

6. 플러그인 사용 (외부 라이브러리)

외부 라이브러리(예: Caffeine, Guava)를 활용하면 더 세부적인 캐싱 전략을 구현할 수 있습니다.
예를 들어, Caffeine은 JVM 기반의 고성능 캐싱 라이브러리입니다.

Caffeine 캐싱 예제

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


---

결론

Ktor에서 캐싱은 HTTP 캐싱 헤더, 서버 측 캐싱, 또는 외부 캐싱 시스템을 사용하여 구현할 수 있습니다.
간단한 캐싱은 HTTP 헤더를 활용하고, 복잡한 캐싱이 필요하다면 Redis나 Caffeine 같은 솔루션을 선택하는 것이 좋습니다.

