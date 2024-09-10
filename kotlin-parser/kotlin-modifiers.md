`KtNamedFunction`과 `KtProperty`에서 modifier(접근 제어자 등)를 가져오려면, Kotlin의 [Kotlin Compiler (KotlinPSI)](https://github.com/JetBrains/kotlin/tree/master/compiler/psi/src/org/jetbrains/kotlin/psi) API를 사용할 수 있습니다. `KtModifierListOwner` 인터페이스를 통해 modifier를 추출할 수 있습니다. 

`KtNamedFunction`과 `KtProperty` 모두 `KtModifierListOwner`를 상속받고 있기 때문에, modifier를 가져오는 방법은 동일합니다.

다음은 `KtNamedFunction`과 `KtProperty`에서 modifier를 가져오는 예시입니다:

```kotlin
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtModifierListOwner

fun getModifiers(function: KtNamedFunction) {
    val modifiers = function.modifierList?.getModifier(KtTokens.PUBLIC_KEYWORD)
    println(modifiers) // 접근 제어자 예: public, private, protected 등
}

fun getPropertyModifiers(property: KtProperty) {
    val modifiers = property.modifierList?.getModifier(KtTokens.PRIVATE_KEYWORD)
    println(modifiers) // 접근 제어자 예: private
}

fun getAllModifiers(element: KtModifierListOwner) {
    val modifierList = element.modifierList
    if (modifierList != null) {
        val modifiers = modifierList.node.getChildren(null).map { it.text }
        println(modifiers) // 모든 modifier를 출력
    }
}
```

### 설명:
1. `modifierList`: `KtNamedFunction` 및 `KtProperty`에서 modifier 목록을 가져오는 속성입니다.
2. `getModifier`: 특정 modifier를 `KtTokens`로 요청할 수 있습니다. 예를 들어, `KtTokens.PUBLIC_KEYWORD`, `KtTokens.PRIVATE_KEYWORD` 등을 사용하여 해당 키워드가 있는지 확인할 수 있습니다.
3. `getAllModifiers`: `KtModifierListOwner`를 사용하여 모든 modifier를 가져오는 방법입니다.

이 코드를 사용하면 `KtNamedFunction`과 `KtProperty`의 modifier 정보를 쉽게 가져올 수 있습니다.