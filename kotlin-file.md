# File filtering
Kotlin에서 파일 목록을 만들 때 파일의 확장자에 따라 Java 파일과 Kotlin 파일을 필터링하고 싶다면, `Files.walk()`를 사용하여 원하는 확장자만 포함되도록 필터를 추가할 수 있습니다. 확장자를 기준으로 파일을 필터링하는 방법은 간단하게 `filter`를 사용하여 처리할 수 있습니다.

예를 들어, `.java`와 `.kt` 파일을 필터링하는 코드를 작성해 보겠습니다:

```kotlin
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.Path
import kotlin.streams.toList

fun listJavaAndKotlinFiles(dirPath: String): List<Path> {
    return Files.walk(Paths.get(dirPath))
        .filter { Files.isRegularFile(it) }  // 파일만 필터링
        .filter { it.toString().endsWith(".java") || it.toString().endsWith(".kt") }  // .java 또는 .kt 파일 필터링
        .toList()
}

fun main() {
    val files = listJavaAndKotlinFiles("/your/directory/path")
    files.forEach { println(it.fileName) }  // 파일 이름 출력
}
```

### 설명:
1. **`Files.walk(Paths.get(dirPath))`**: 지정한 디렉토리의 파일과 하위 디렉토리를 재귀적으로 탐색합니다.
2. **`filter { Files.isRegularFile(it) }`**: 파일만 선택하고, 디렉토리는 제외합니다.
3. **`filter { it.toString().endsWith(".java") || it.toString().endsWith(".kt") }`**: `.java` 또는 `.kt`로 끝나는 파일들만 필터링합니다.
4. **`toList()`**: 스트림을 리스트로 변환하여 결과를 반환합니다.
5. **`it.fileName`**: 파일 이름을 출력합니다.

이렇게 하면 주어진 디렉토리에서 `.java`와 `.kt` 확장자를 가진 파일만을 손쉽게 리스트로 만들 수 있습니다.

# readString()

`Path` 객체를 사용하여 파일의 내용을 읽으려면 `Files.readAllBytes()` 또는 `Files.readString()`과 같은 메서드를 사용할 수 있습니다. Kotlin의 `readText()`는 `File` 객체에 사용되지만, `Path`를 사용할 때는 `java.nio.file.Files`의 메서드를 활용하는 것이 좋습니다.

다음은 `Path` 객체로 파일의 내용을 텍스트로 읽는 방법입니다:

```kotlin
import java.nio.file.Files
import java.nio.file.Paths

fun readFileAsText(filePath: String): String {
    val path = Paths.get(filePath)
    return Files.readString(path)  // 파일의 내용을 텍스트로 읽기
}

fun main() {
    val text = readFileAsText("/your/file/path.txt")
    println(text)
}
```

여기서 `Files.readString(path)`는 파일의 전체 내용을 `String`으로 반환해 줍니다. 이 메서드는 Java 11 이상에서 사용할 수 있으며, 만약 Java 11 이전 버전을 사용 중이라면 `Files.readAllBytes(path)`를 사용해 파일 내용을 `ByteArray`로 읽은 다음 이를 문자열로 변환해야 합니다:

```kotlin
import java.nio.file.Files
import java.nio.file.Paths

fun readFileAsText(filePath: String): String {
    val path = Paths.get(filePath)
    return String(Files.readAllBytes(path))  // 바이트 배열을 읽고 String으로 변환
}

fun main() {
    val text = readFileAsText("/your/file/path.txt")
    println(text)
}
```

이렇게 하면 `Path` 객체로부터 텍스트 파일의 내용을 쉽게 읽을 수 있습니다.


# `it`

코드에서 `it`는 **람다 표현식에서 사용되는 기본 변수**입니다. Kotlin에서는 람다 표현식에서 매개변수가 하나일 때 `it`이라는 이름으로 자동으로 제공됩니다.

해당 예시에서 `Files.walk()`는 `Stream<Path>`를 반환하고, 이 스트림은 디렉토리와 파일 경로들을 차례로 제공합니다. `filter { it -> Files.isRegularFile(it) }` 구문에서 `it`은 이 `Stream`에서 나온 **각 파일 경로(`Path`)** 를 나타냅니다.

따라서 `it`은 각각의 파일 또는 디렉토리 경로를 나타내고, `Files.isRegularFile(it)`은 그 경로가 파일인지 확인하는 역할을 합니다.

다음과 같이 `it` 대신 명시적으로 변수명을 사용할 수도 있습니다:

```kotlin
.filter { path -> Files.isRegularFile(path) }
```

이 경우 `it` 대신 `path`라는 이름을 사용하여 더 읽기 쉽게 표현할 수 있습니다.


#
Kotlin에서 파일 목록을 재귀적으로 나열하는 방법 외에도, 표준 라이브러리를 활용하면 조금 더 간결하고 쉽게 파일 목록을 가져올 수 있습니다. Kotlin의 `java.nio.file` 패키지와 `Files.walk()` 메서드를 사용하면 재귀 호출 없이도 디렉토리 내 모든 파일을 쉽게 리스트화할 수 있습니다.

다음은 `Files.walk()`를 사용한 예시입니다:

```kotlin
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.Path
import kotlin.streams.toList

fun listFilesInDirectory(dirPath: String): List<Path> {
    return Files.walk(Paths.get(dirPath))
        .filter { Files.isRegularFile(it) }  // 파일만 필터링
        .toList()
}

fun main() {
    val files = listFilesInDirectory("/your/directory/path")
    files.forEach { println(it) }
}
```

이 코드는 주어진 디렉토리 내의 모든 파일을 재귀적으로 검색하지만, 직접 재귀적으로 함수를 호출할 필요는 없습니다. `Files.walk()`는 내부적으로 재귀 처리를 해주며, `Stream`을 반환하기 때문에 편리하게 처리할 수 있습니다. `filter`를 사용해 원하는 조건으로 파일만 필터링할 수도 있습니다.

이 방법을 사용하면 재귀 호출 없이도 파일 목록을 한 번에 얻을 수 있어 더 직관적이고 효율적일 수 있습니다.


#
Kotlin에서 특정 폴더를 순회하면서 그 안의 파일과 폴더를 처리하는 코드를 작성할 수 있습니다. 이를 위해 `File` 클래스를 사용하면 됩니다. Kotlin의 표준 라이브러리에서는 Java의 파일 I/O를 지원하므로 Java의 `File` API를 그대로 사용할 수 있습니다.

다음은 폴더 내 파일과 폴더를 재귀적으로 순회하는 예시입니다:

```kotlin
import java.io.File

fun traverseFolder(folder: File) {
    if (folder.exists() && folder.isDirectory) {
        // 폴더 내의 파일과 폴더를 순회
        folder.listFiles()?.forEach { file ->
            if (file.isDirectory) {
                println("Directory: ${file.absolutePath}")
                // 재귀적으로 하위 폴더 탐색
                traverseFolder(file)
            } else {
                println("File: ${file.absolutePath}")
            }
        }
    } else {
        println("${folder.absolutePath} is not a directory or doesn't exist.")
    }
}

fun main() {
    val folderPath = "your/folder/path"  // 여기에 순회하려는 폴더 경로를 넣으세요
    val folder = File(folderPath)

    traverseFolder(folder)
}
```

### 설명:
1. `File(folderPath)`: 폴더 경로로 `File` 객체를 생성합니다.
2. `listFiles()`: 폴더 안의 파일 및 하위 폴더를 `File` 객체 배열로 반환합니다.
3. `isDirectory`: 현재 `File` 객체가 폴더인지 확인합니다.
4. `isFile`: 현재 `File` 객체가 파일인지 확인합니다.
5. 재귀적으로 `traverseFolder` 함수를 호출하여 하위 폴더도 순회할 수 있습니다.

위 코드는 폴더와 파일을 재귀적으로 탐색하면서 경로를 출력합니다.