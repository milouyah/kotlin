# Comment

`PsiTreeUtil.collectElementsOfType`은 기본적으로 모든 자식 노드를 포함한 전체 트리에서 요소를 수집합니다. 하지만, 자식 노드를 제외하고 최상위 노드에서만 주석을 얻으려면, 트리를 직접 순회하면서 자식 노드를 무시하는 방식으로 주석을 추출해야 합니다.

이를 위해 **직접 트리를 순회하면서** 특정 깊이의 노드만 처리하거나, **`KtFile`의 직속 자식**들만 대상으로 처리하는 방법을 사용할 수 있습니다.

### 최상위 노드에서만 주석 추출하기

`KtFile`의 **직속 자식 노드**만을 순회하면서 `PsiComment` 타입의 노드만 추출하는 코드를 작성할 수 있습니다.

다음은 최상위 주석(즉, 직속 자식 주석)만 추출하는 예시입니다:

```kotlin
import com.intellij.psi.PsiComment
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.psiUtil.children

// 최상위 주석만 추출하는 함수
fun extractTopLevelComments(ktFile: KtFile): List<String> {
    // KtFile의 직속 자식들만 순회
    return ktFile.children
        .filterIsInstance<PsiComment>()  // PsiComment 타입의 노드만 필터링
        .map { it.text }  // 주석의 텍스트만 추출
}
```

### 사용 예시:

```kotlin
fun main() {
    val kotlinFileContent = """
        package com.example.test
        
        // This is a top-level comment
        class MyClass {
            /*
             This is a multi-line comment inside the class, not top-level
             */
            fun myFunction() {
                // This is a single-line comment inside a function
            }
        }
    """.trimIndent()

    val ktFile = createKotlinFile("MyClass.kt", kotlinFileContent, project)

    // 최상위 주석 추출
    val topLevelComments = extractTopLevelComments(ktFile)

    // 추출된 최상위 주석 출력
    topLevelComments.forEach { comment ->
        println("Top-level Comment: $comment")
    }
}
```

### 설명:

1. **`ktFile.children`**: `ktFile`의 직속 자식 노드들만 가져옵니다. 즉, 클래스 내부나 함수 내부에 있는 자식 노드는 제외하고 최상위 노드만 순회합니다.
2. **`filterIsInstance<PsiComment>()`**: 자식들 중에서 `PsiComment` 타입의 노드들만 필터링합니다.
3. **`map { it.text }`**: 각 주석의 텍스트만 추출하여 리스트로 반환합니다.

### 주의 사항:
- 이 코드는 **최상위 수준의 주석**(클래스 선언 바깥이나 패키지 선언 옆에 있는 주석)만 추출합니다.
- 클래스나 함수 내부에 있는 주석은 포함되지 않으므로, 특정 위치의 주석만 파싱하고자 할 때 유용합니다.

이렇게 하면 하위(자식) 노드를 무시하고 최상위 주석만 가져올 수 있습니다.



`KtFile`에서 주석(comment)을 추출하고 파싱하려면 Kotlin PSI(Program Structure Interface)를 사용해야 합니다. Kotlin PSI는 Kotlin 코드의 구조를 분석할 수 있는 API를 제공하며, 주석 역시 특정 PSI 노드로 표현됩니다. 주석은 일반적으로 `PsiComment` 타입의 객체로 저장됩니다.

### 주석 추출 방법
1. **`KtFile`의 모든 자식 노드를 순회**하면서 `PsiComment` 타입의 노드를 찾아내는 방법을 사용할 수 있습니다.
2. **`PsiTreeUtil`** 또는 `KtVisitor`를 사용해 주석을 방문하여 추출할 수 있습니다.

### 주석 추출 및 파싱 예시

```kotlin
import com.intellij.psi.PsiComment
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.kotlin.psi.KtFile

// 주석을 추출하는 함수
fun extractComments(ktFile: KtFile): List<String> {
    // PsiComment 타입의 모든 주석을 추출
    val comments = PsiTreeUtil.collectElementsOfType(ktFile, PsiComment::class.java)
    
    // 주석의 텍스트만 추출하여 리스트로 반환
    return comments.map { it.text }
}
```

### 사용 예시:

```kotlin
fun main() {
    val kotlinFileContent = """
        package com.example.test
        
        // This is a class
        class MyClass {
            /*
             This is a multi-line comment
             */
            fun myFunction() {
                // This is a single-line comment inside a function
            }
        }
    """.trimIndent()

    val ktFile = createKotlinFile("MyClass.kt", kotlinFileContent, project)

    // 주석 추출
    val comments = extractComments(ktFile)

    // 추출된 주석 출력
    comments.forEach { comment ->
        println("Comment: $comment")
    }
}
```

### 설명:
1. **`PsiTreeUtil.collectElementsOfType`**: 이 메서드는 `ktFile`에서 특정 타입(`PsiComment`)의 모든 노드를 수집합니다. `PsiComment`는 코드의 주석을 나타냅니다.
2. **`text`**: 각 주석 객체의 `text` 속성을 사용하여 실제 주석 내용을 문자열로 추출합니다.

### 주석 파싱
추출한 주석 내용을 파싱하려면, 각 주석의 내용을 문자열로 변환한 후 원하는 방식으로 처리할 수 있습니다. 예를 들어 주석의 형식을 분석하거나 특정 태그(예: TODO, FIXME)를 찾을 수 있습니다.

#### 예시: TODO 주석 찾기

```kotlin
fun parseTodoComments(comments: List<String>): List<String> {
    return comments.filter { it.contains("TODO") }
}
```

### 결론
- **주석 추출**: `PsiTreeUtil`을 사용하여 `KtFile`에서 `PsiComment` 노드를 추출.
- **주석 파싱**: 추출된 주석을 문자열로 변환하고, 원하는 규칙을 적용해 파싱 가능.

이 방법을 사용하면 Kotlin 파일에서 주석을 추출하고, 이를 원하는 방식으로 파싱할 수 있습니다.