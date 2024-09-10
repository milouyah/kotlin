Kotlin에서 리스트의 각 항목을 함수의 첫 번째 인자로 전달하면서 해당 함수 호출을 수행하려면 `forEach`와 같은 컬렉션 함수를 활용할 수 있습니다. 예를 들어, 함수가 세 개의 파라미터를 받고, 그 중 첫 번째 파라미터는 리스트의 각 항목으로 채우고 싶다면, 이를 `forEach` 또는 `map`으로 처리할 수 있습니다.

### 예제 1: `forEach`를 사용하는 방법

다음은 리스트의 각 항목을 첫 번째 인자로 전달하여 함수를 호출하는 방법입니다.

```kotlin
fun myFunction(param1: String, param2: Int, param3: Boolean) {
    println("param1: $param1, param2: $param2, param3: $param3")
}

fun main() {
    val itemList = listOf("item1", "item2", "item3")
    
    itemList.forEach { item ->
        myFunction(item, 42, true)  // 첫 번째 인자로 리스트의 항목 사용
    }
}
```

### 설명:
- **`itemList.forEach { item -> ... }`**: 리스트의 각 항목(`item`)을 순회하며, `myFunction`에 첫 번째 인자로 전달합니다.
- **`myFunction(item, 42, true)`**: 첫 번째 인자에 리스트 항목을 넣고, 두 번째와 세 번째 인자는 고정된 값(여기서는 42와 true)을 사용합니다.

### 출력:
```
param1: item1, param2: 42, param3: true
param1: item2, param2: 42, param3: true
param1: item3, param2: 42, param3: true
```

### 예제 2: `map`을 사용해 호출 결과를 리스트로 변환하는 방법

리스트의 각 항목을 사용해 함수를 호출하고 그 결과를 리스트로 만들고 싶다면 `map`을 사용할 수 있습니다.

```kotlin
fun myFunction(param1: String, param2: Int, param3: Boolean): String {
    return "Result of $param1"
}

fun main() {
    val itemList = listOf("item1", "item2", "item3")
    
    val results = itemList.map { item ->
        myFunction(item, 42, true)  // 첫 번째 인자로 리스트의 항목 사용
    }
    
    println(results)  // 결과 리스트 출력
}
```

### 설명:
- **`itemList.map { item -> ... }`**: 각 항목을 사용해 함수를 호출하고, 그 결과를 리스트로 변환합니다.

### 출력:
```
[Result of item1, Result of item2, Result of item3]
```

이렇게 하면, 리스트의 각 항목을 함수의 첫 번째 인자로 사용하여 함수 호출을 반복적으로 수행할 수 있습니다. `forEach`는 결과를 반환하지 않고 단순히 함수 호출만을 수행하는 반면, `map`은 함수의 결과를 모아서 새로운 리스트를 반환합니다.