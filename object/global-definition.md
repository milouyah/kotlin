Kotlin에서 `targetFolder`와 같은 경로를 전역적으로 정의하려면, 프로젝트 전역에서 접근할 수 있는 방식으로 설정해야 합니다. 이를 위해 다음과 같은 몇 가지 방법을 사용할 수 있습니다:

### 1. **`object` 사용**
`object`는 싱글톤 패턴을 따르기 때문에, `targetFolder`를 전역적으로 사용할 수 있습니다.

```kotlin
object Config {
    const val TARGET_FOLDER = "/your/target/folder/path"
}
```

이렇게 정의한 `TARGET_FOLDER`는 어디서든 아래와 같이 접근할 수 있습니다:

```kotlin
val path = Config.TARGET_FOLDER
```

### 2. **`companion object` 사용**
`companion object`는 클래스와 연관된 정적 멤버를 정의할 때 사용됩니다. 특정 클래스 내에서 전역적으로 사용하는 경우 적합합니다.

```kotlin
class FolderManager {
    companion object {
        const val TARGET_FOLDER = "/your/target/folder/path"
    }
}
```

클래스의 인스턴스를 생성하지 않고도, `TARGET_FOLDER`에 바로 접근할 수 있습니다:

```kotlin
val path = FolderManager.TARGET_FOLDER
```

### 3. **환경 변수나 시스템 속성 사용**
만약 `targetFolder` 경로가 애플리케이션 실행 시점에서 동적으로 설정되기를 원한다면, 시스템 환경 변수나 JVM 시스템 속성을 사용할 수 있습니다.

```kotlin
val targetFolder = System.getenv("TARGET_FOLDER") ?: "/default/target/folder"
```

또는 JVM 시스템 속성을 사용할 수도 있습니다:

```kotlin
val targetFolder = System.getProperty("target.folder", "/default/target/folder")
```

이 방법은 경로를 외부에서 설정할 수 있도록 하여 유연성을 제공합니다.

### 4. **DI (Dependency Injection) 사용**
만약 프로젝트에서 의존성 주입 프레임워크(Koin, Dagger)를 사용하고 있다면, `targetFolder`를 주입 가능하게 설정할 수도 있습니다.

예를 들어 Koin을 사용하는 경우:

```kotlin
val appModule = module {
    single { "/your/target/folder/path" }
}
```

필요한 곳에서 주입하여 사용합니다:

```kotlin
val targetFolder: String by inject()
```

### 결론:
프로젝트의 구조와 요구 사항에 따라 위 방법 중 하나를 선택하면 됩니다. 가장 간단하고 많이 사용되는 방식은 `object`를 사용하는 첫 번째 방법입니다. 하지만 경로를 외부에서 설정할 수 있는 유연성이 필요하다면 환경 변수를 활용하는 것도 좋은 방법입니다.