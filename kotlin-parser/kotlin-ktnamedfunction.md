`KtNamedFunction`은 Kotlin의 PSI(Program Structure Interface) 구조에서 함수 선언을 나타내는 클래스입니다. 이를 통해 함수의 반환 타입, 매개변수, 이름 등을 파싱할 수 있습니다.

`KtNamedFunction`으로부터 반환 값과 매개변수를 얻기 위해서는 Kotlin PSI API와 함께 활용할 수 있습니다. 아래는 그 방법입니다.

### 1. 함수의 반환 타입 얻기

`KtNamedFunction`의 `typeReference`를 사용하여 함수의 반환 타입을 얻을 수 있습니다. 반환 타입이 명시되지 않은 경우에는 `null`이 반환됩니다.

```kotlin
val returnType = namedFunction.typeReference?.text
```

### 2. 함수의 매개변수 얻기

함수의 매개변수는 `valueParameters` 속성을 통해 리스트로 얻을 수 있습니다. 각각의 매개변수는 `KtParameter` 타입이며, 이를 통해 매개변수 이름, 타입 등을 접근할 수 있습니다.

```kotlin
val parameters = namedFunction.valueParameters
parameters.forEach { parameter ->
    val paramName = parameter.name
    val paramType = parameter.typeReference?.text
    println("Parameter name: $paramName, type: $paramType")
}
```

### 전체 예제

다음은 `KtNamedFunction`으로부터 함수의 이름, 반환 타입, 매개변수를 출력하는 예제입니다.

```kotlin
fun extractFunctionInfo(namedFunction: KtNamedFunction) {
    // 함수 이름
    val functionName = namedFunction.name
    println("Function name: $functionName")

    // 반환 타입
    val returnType = namedFunction.typeReference?.text ?: "Unit"
    println("Return type: $returnType")

    // 매개변수
    val parameters = namedFunction.valueParameters
    parameters.forEach { parameter ->
        val paramName = parameter.name
        val paramType = parameter.typeReference?.text ?: "Unknown"
        println("Parameter name: $paramName, type: $paramType")
    }
}
```

이 코드를 통해 함수의 이름, 반환 타입, 매개변수를 모두 추출할 수 있습니다. `KtNamedFunction`의 PSI 요소와 관련된 API들을 활용해 필요한 정보를 얻을 수 있습니다.

이 코드가 작동하려면 Kotlin PSI를 사용할 수 있는 환경이 필요하며, 보통 Kotlin 플러그인이나 컴파일러 플러그인 등을 개발하는 경우에 사용됩니다.