# Sealed Class

Kotlin의 **sealed class**는 `상속 계층을 제한`하고 특정 클래스 계층을 정의하는 데 사용되는 특수한 클래스입니다. sealed 키워드를 사용하여 선언되며, 이를 통해 하위 클래스들을 제한적으로 정의할 수 있습니다.

## 주요 특징

1. 상속 계층 제한
sealed class는 **같은 파일 안에서만 하위 클래스를 정의**할 수 있습니다. 이를 통해 허용된 클래스만 상속받을 수 있게 하여 코드의 안정성과 가독성을 높입니다.


2. 컴파일러의 검사 지원
sealed class는 컴파일러가 하위 클래스를 모두 알고 있기 때문에 when 표현식 등에서 모든 하위 클래스가 처리되었는지 확인할 수 있습니다. 따라서 런타임 에러를 줄이고 안전성을 보장합니다.


3. 추상 클래스와 유사
sealed class `자체는 인스턴스화할 수 없으며`, `추상 클래스처럼 동작`합니다. 하지만 추상 클래스보다 `더 강력한 상속 제한`을 제공합니다.


----


## 문법

```kotlin
sealed class Result {
    data class Success(val data: String) : Result()
    data class Error(val message: String) : Result()
    object Loading : Result()
}
```

Result는 sealed class로 선언되었고, 하위 클래스인 Success, Error, Loading은 같은 파일 안에서 정의되었습니다.

하위 클래스는 일반 클래스(class), 데이터 클래스(data class), 객체(object) 등 다양한 형태로 정의할 수 있습니다.


---

## 사용 예시

when 표현식에서 활용

```kotlin
fun handleResult(result: Result) {
    when (result) {
        is Result.Success -> println("Success: ${result.data}")
        is Result.Error -> println("Error: ${result.message}")
        is Result.Loading -> println("Loading...")
        // `else`가 필요 없음: 모든 경우가 처리됨
    }
}
```

sealed class 덕분에 컴파일러가 when 표현식에서 모든 경우를 강제합니다.



---

## sealed class의 주요 용도

1. 상태 관리: 예를 들어, UI 상태를 관리할 때 사용 (Loading, Success, Error 등).


2. 데이터 모델링: 특정 데이터의 불변 집합을 나타낼 때.


3. 안전한 타입 체크: when 구문과 함께 사용하여 런타임 예외 방지.




---

## sealed interface (Kotlin 1.5 이상)

Kotlin 1.5부터는 sealed 키워드가 인터페이스에도 적용됩니다.

```kotlin
sealed interface Event
data class Click(val x: Int, val y: Int) : Event
data class Scroll(val offset: Int) : Event
object Idle : Event
```

인터페이스로도 비슷한 방식으로 계층을 제한할 수 있습니다.


sealed class는 특히 상태 관리나 제한적인 상속 계층 구조가 필요한 상황에서 매우 유용한 도구입니다!

