# Companion Object
Kotlin에서 `companion object`는 클래스 내부에서 정적 멤버(static member) 또는 메서드처럼 동작하는 객체를 선언할 때 사용됩니다. Kotlin에는 `static` 키워드가 없기 때문에, 대신 `companion object`를 사용하여 Java의 정적 멤버와 유사한 기능을 구현합니다.

`companion object`의 주요 특징은 다음과 같습니다:

1. **클래스당 하나만 존재**: 한 클래스 안에 하나의 `companion object`만 선언할 수 있습니다. 클래스의 모든 인스턴스가 공유하는 공통 상태나 동작을 정의할 때 사용됩니다.

2. **정적 멤버처럼 접근**: `companion object`에 선언된 멤버는 클래스 이름을 통해 정적 멤버처럼 접근할 수 있습니다. 예를 들어, `MyClass.myMethod()` 형태로 접근이 가능합니다.

3. **인터페이스 구현 가능**: `companion object`는 인터페이스를 구현할 수 있습니다. 이 경우 해당 클래스의 정적 메서드처럼 동작하지만, 여전히 클래스 인스턴스에 종속되지 않고 동작합니다.

4. **팩토리 메서드**: `companion object`는 주로 팩토리 메서드를 만들 때 사용됩니다. 이는 클래스의 생성 로직을 외부에서 통제하고 싶을 때 유용합니다.

### 예시

```kotlin
class MyClass {
    companion object {
        const val CONSTANT = "This is a constant"

        fun create(): MyClass {
            return MyClass()
        }
    }
}

fun main() {
    // Companion object를 통해 클래스 이름으로 정적 메서드에 접근
    val instance = MyClass.create()
    
    // Companion object의 상수에 접근
    println(MyClass.CONSTANT)
}
```

이 예시에서 `create()` 메서드는 `companion object`에 정의되어 있으므로, 클래스 이름을 통해 정적 메서드처럼 호출할 수 있습니다. 또한 `CONSTANT` 상수도 `companion object`에 정의되어 정적으로 접근할 수 있습니다.

### 결론

Kotlin의 `companion object`는 클래스 내에서 정적 멤버와 메서드를 정의할 수 있는 유연한 방법을 제공합니다. 특히, Java의 `static` 키워드와 유사한 역할을 하지만 Kotlin의 객체 지향 패러다임에 맞게 좀 더 개선된 형태로 제공됩니다.