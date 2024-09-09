파일 이름을 기준으로 `.kt` 확장자를 통해 Kotlin 파일인지 확인하는 Kotlin 코드는 아래와 같습니다:

```kotlin
fun isKotlinFile(fileName: String): Boolean {
    return fileName.endsWith(".kt", ignoreCase = true)
}

fun main() {
    val fileName1 = "example.kt"
    val fileName2 = "example.java"

    println("$fileName1 is Kotlin file: ${isKotlinFile(fileName1)}") // true
    println("$fileName2 is Kotlin file: ${isKotlinFile(fileName2)}") // false
}
```

이 코드는 `fileName`이 `.kt` 확장자로 끝나는지 여부를 확인하여 Kotlin 파일인지 판단합니다.