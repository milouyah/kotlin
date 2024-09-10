# Null string -> default
Kotlin에서 문자열이 `null`인 경우 기본값으로 공백 문자열을 설정하려면 `?:` 엘비스 연산자를 사용할 수 있습니다. 예를 들어, 아래와 같이 `null` 값인 경우 공백 문자열로 대체할 수 있습니다:

```kotlin
val str: String? = null
val result: String = str ?: ""
println(result)  // 출력: ""
```

위 코드에서 `str`이 `null`인 경우 `?:` 연산자를 통해 기본값으로 빈 문자열 `""`이 할당됩니다.