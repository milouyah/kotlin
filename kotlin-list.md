# List filter
네, Kotlin에서 일반 리스트(List)에 `filter` 함수를 사용할 수 있습니다. `filter` 함수는 리스트의 요소 중 주어진 조건에 맞는 요소들만 남긴 새로운 리스트를 반환합니다.

예시 코드입니다:

```kotlin
val numbers = listOf(1, 2, 3, 4, 5, 6)
val evenNumbers = numbers.filter { it % 2 == 0 }

println(evenNumbers)  // 출력: [2, 4, 6]
```

이 코드에서 `filter`는 리스트 `numbers`에서 짝수인 요소들만 걸러서 새로운 리스트 `evenNumbers`를 만듭니다.