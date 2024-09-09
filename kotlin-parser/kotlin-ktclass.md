`KtClass`에서 메서드를 추출하려면 Kotlin PSI (Program Structure Interface)를 사용하여 구체적으로 `KtNamedFunction` 객체를 찾으면 됩니다. 이를 위해 `KtClass` 내부의 함수들을 검색하는 방법을 사용하면 됩니다.

아래는 예시 코드입니다:

```kotlin
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtNamedFunction

fun extractMethodsFromClass(ktClass: KtClass): List<KtNamedFunction> {
    return ktClass.declarations.filterIsInstance<KtNamedFunction>()
}
```

이 코드는 `KtClass`의 `declarations` 속성에서 `KtNamedFunction` 타입의 객체만 필터링하여 메서드를 추출합니다.

이를 통해 해당 클래스에 정의된 모든 메서드를 리스트로 반환할 수 있습니다.