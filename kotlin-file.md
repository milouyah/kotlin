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