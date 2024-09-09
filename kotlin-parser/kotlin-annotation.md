Kotlin의 기본적인 IntelliJ PSI API에서는 **주석 내에서 애노테이션을 추출하는 유틸리티 클래스**가 따로 제공되지는 않습니다. 주석은 일반 텍스트로 간주되며, 주석 내부의 내용을 파싱하는 기능은 자동으로 제공되지 않기 때문에 직접 주석 텍스트를 파싱해야 합니다.

그러나 **Kotlin 코드에서 실제 애노테이션(코드의 어노테이션)**을 처리하는 도구들은 있습니다. 예를 들어, `KtAnnotationEntry`는 애노테이션을 나타내며, 이를 통해 실제 코드 상의 어노테이션을 다룰 수 있습니다. 하지만 주석 내의 애노테이션(예: `// @Author John Doe`)을 처리하는 전용 도구는 제공되지 않기 때문에 일반적인 텍스트 파싱 방식으로 처리해야 합니다.

### 실제 어노테이션 추출 (`KtAnnotationEntry`)

만약 **주석이 아닌 실제 코드 상의 애노테이션**을 추출하고 싶다면, 다음과 같이 할 수 있습니다:

```kotlin
import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.psiUtil.collectDescendantsOfType

// 코드에서 애노테이션을 추출하는 함수
fun extractAnnotationsFromCode(ktFile: KtFile): List<String> {
    // KtAnnotationEntry는 코드 상의 실제 애노테이션을 나타냅니다.
    return ktFile.collectDescendantsOfType<KtAnnotationEntry>()
        .map { it.text } // 애노테이션 텍스트를 추출
}
```

### 사용 예시:

```kotlin
fun main() {
    val kotlinFileContent = """
        package com.example.test

        @Author(name = "John Doe")
        class MyClass {
            @Deprecated("This function is deprecated")
            fun myFunction() {
                // Function body
            }
        }
    """.trimIndent()

    val ktFile = createKotlinFile("MyClass.kt", kotlinFileContent, project)

    // 코드에서 애노테이션 추출
    val annotations = extractAnnotationsFromCode(ktFile)

    // 추출된 애노테이션 출력
    annotations.forEach { annotation ->
        println("Annotation: $annotation")
    }
}
```

### 설명:
- **`KtAnnotationEntry`**: 실제 Kotlin 코드에서 사용된 애노테이션을 나타냅니다. 주석 내의 애노테이션과는 달리, 실제 코드 상에서 어노테이션으로 선언된 부분만 추출됩니다.
- **`collectDescendantsOfType`**: PSI 트리 내에서 특정 타입의 노드를 수집하는 편리한 함수입니다. 이 경우, `KtAnnotationEntry` 타입의 노드를 찾아서 애노테이션을 추출합니다.

### 결론:
- **주석 내 애노테이션**: 따로 유틸리티 클래스가 없으므로, 주석 내의 애노테이션은 텍스트로 처리해야 합니다. 즉, `PsiComment`에서 텍스트를 추출한 후 정규식을 사용해 파싱해야 합니다.
- **실제 코드 내 애노테이션**: 코드 상의 애노테이션은 `KtAnnotationEntry`를 통해 쉽게 추출할 수 있습니다.