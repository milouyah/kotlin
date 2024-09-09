네, `KtClass`에서 메서드를 추출할 때는 `getDeclarations()` 메서드를 사용하는 것이 맞습니다. 이 메서드는 해당 클래스에 선언된 멤버들(필드, 메서드, 이너 클래스 등)을 반환하며, 이를 통해 메서드를 추출할 수 있습니다.

`children`은 모든 PSI 요소를 포함하기 때문에, 이를 사용할 경우 주석이나 다른 불필요한 요소들도 포함될 수 있습니다. 따라서 메서드와 같은 구체적인 선언을 추출하려면 `getDeclarations()`를 사용하는 것이 적합합니다.

아래는 `children`과 `declarations`의 차이를 보여주는 예입니다:

```kotlin
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtProperty

fun extractMethodsFromClass(ktClass: KtClass): List<KtNamedFunction> {
    // KtClass의 declarations에서 메서드만 추출
    return ktClass.declarations.filterIsInstance<KtNamedFunction>()
}

fun extractAllChildren(ktClass: KtClass): List<Any> {
    // 모든 children을 추출하는 경우
    return ktClass.children.toList()
}
```

- `declarations`: 클래스 내부의 메서드, 필드, 이너 클래스 등 의미 있는 선언을 포함합니다.
- `children`: 해당 클래스의 모든 하위 PSI 노드를 포함하며, 주석, 공백, 키워드 등도 포함될 수 있습니다.

메서드와 같은 선언을 얻고 싶다면 `getDeclarations()`를 사용하는 것이 더 효율적입니다.


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