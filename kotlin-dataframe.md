# Dataframe
Kotlin DataFrame에서 `DataFrame`을 생성하고 String 타입의 `name`, Int 타입의 `age`, String 타입의 `school` 컬럼을 추가한 후, 몇 개의 row를 추가하는 코드는 다음과 같습니다.

```kotlin
import org.jetbrains.kotlinx.dataframe.api.*

fun main() {
    // 데이터 프레임 생성
    val df = dataFrameOf(
        "name", "age", "school" // 컬럼 이름
    )(
        "Alice", 20, "High School",  // 첫 번째 row
        "Bob", 22, "University",     // 두 번째 row
        "Charlie", 19, "High School" // 세 번째 row
    )

    // 데이터 프레임 출력
    println(df)
}
```

이 코드는 `kotlinx-dataframe` 라이브러리를 사용하여 데이터 프레임을 생성하고, 세 개의 row를 추가합니다. `dataFrameOf`를 이용해 컬럼을 정의하고, 해당 컬럼의 값을 추가할 수 있습니다.

이 라이브러리를 사용하기 위해서는 `build.gradle.kts` 파일에 다음과 같은 종속성을 추가해야 합니다:

```kotlin
dependencies {
    implementation("org.jetbrains.kotlinx:dataframe:0.8.0") // 최신 버전 확인 필요
}
```