`KtProperty`는 Kotlin의 변수 선언을 나타내는 PSI 클래스입니다. `KtProperty`로부터 반환 타입(즉, 변수의 타입)을 가져오기 위해서는 변수의 **타입 참조** 또는 **초기화된 값**을 분석할 수 있습니다. 이를 위해 주로 `typeReference`와 `initializer`를 사용합니다.

### 1. 변수의 타입 (`typeReference`)을 통해 반환 타입을 얻기

변수의 타입이 명시적으로 선언된 경우 `typeReference`를 사용하여 타입 정보를 가져올 수 있습니다.

```kotlin
val propertyType = ktProperty.typeReference?.text
```

### 2. 변수의 초기화 값 (`initializer`)을 통해 타입 추론하기

변수의 타입이 명시적으로 선언되지 않고 초기화된 값에 의해 추론되는 경우, `initializer`로 초기화 값을 확인할 수 있습니다. 여기에서 더 깊이 들어가려면 초기화된 표현식을 분석해야 하지만, 간단히 `initializer?.text`로 값을 가져올 수 있습니다.

```kotlin
val initializer = ktProperty.initializer?.text
```

### 전체 예제

다음은 `KtProperty`로부터 변수의 이름, 반환 타입(변수의 타입), 초기화 값을 출력하는 예제입니다.

```kotlin
fun extractPropertyInfo(ktProperty: KtProperty): String {
    // 변수 이름
    val propertyName = ktProperty.name ?: "UnnamedProperty"

    // 타입 참조가 존재하는지 확인 (명시적 타입)
    val propertyType = ktProperty.typeReference?.text ?: "UnknownType"

    // 초기화 값
    val initializer = ktProperty.initializer?.text ?: "NoInitializer"

    // 최종 결과 문자열
    return "$propertyName: $propertyType = $initializer"
}
```

### 설명:
1. **`ktProperty.typeReference?.text ?: "UnknownType"`**: 타입 참조가 있으면 그 타입을 반환하고, 없으면 `"UnknownType"`으로 처리합니다.
2. **`ktProperty.initializer?.text ?: "NoInitializer"`**: 초기화 값이 존재하지 않으면 `"NoInitializer"`로 처리합니다.

### 예시 출력:

만약 다음과 같은 변수가 있다고 가정해 봅시다:

```kotlin
val age: Int = 30
```

위 코드를 실행하면 다음과 같은 문자열이 반환됩니다:

```
age: Int = 30
```

만약 타입이 명시되지 않았지만 초기화가 되어 있다면:

```kotlin
val name = "John"
```

이 경우 출력은:

```
name: UnknownType = "John"
```

이 코드는 `KtProperty`로부터 변수의 타입과 초기화 값을 파싱하여 하나의 문자열로 출력하는 방법을 보여줍니다.