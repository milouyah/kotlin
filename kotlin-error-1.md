# Q1
Kotlin에서,
Can't access 'java.lang.Object' which is a supertype of 'org.jetbrains.kotlin.com.intellij.openai.util.Disposer'. Check your module classpath for missing or confliting depedencies라는데,
어떤 문제가 있는저깆?

Disposer.newDisposable()ㅡㄹ 호출할 떄 발생하는 문제야


# A1
해당 에러는 Kotlin에서 `Disposer.newDisposable()`을 호출할 때 발생하는 클래스 경로(Classpath) 문제로 보입니다. 일반적으로, 이 오류는 다음과 같은 원인으로 발생할 수 있습니다.

1. **종속성 충돌**: `java.lang.Object`는 모든 클래스의 상위 클래스이기 때문에 이 클래스를 찾을 수 없다는 것은 일반적으로 모듈 간의 종속성 충돌이 발생했음을 의미합니다. 특히 `org.jetbrains.kotlin.com.intellij` 패키지와 관련된 라이브러리 버전 간 충돌이 있을 수 있습니다.

2. **누락된 라이브러리**: 특정 라이브러리가 classpath에 포함되지 않았을 때 발생할 수 있습니다. 특히, `Disposer` 클래스는 `IntelliJ Platform`의 API 중 하나이기 때문에, 해당 라이브러리가 프로젝트에 제대로 추가되지 않았을 수 있습니다.

3. **IDE 설정 문제**: IntelliJ와 같은 개발 환경에서 모듈 설정이 올바르게 되지 않았을 수 있습니다. 이 경우, 모듈의 `classpath`가 제대로 설정되지 않았거나, 모듈 간의 종속성이 충돌할 수 있습니다.

### 해결 방법

1. **Gradle 또는 Maven 종속성 확인**:
   프로젝트에서 사용하는 빌드 시스템(예: Gradle, Maven 등)에서 `intellij-core` 또는 `kotlin-compiler`와 관련된 종속성이 충돌하는지 확인하세요.

   예를 들어, `gradle`을 사용하는 경우 `build.gradle` 파일에서 종속성 버전을 확인하고, 필요한 경우 최신 버전으로 업데이트하세요.

   ```groovy
   dependencies {
       implementation "org.jetbrains.kotlin:kotlin-stdlib:1.6.0"
       implementation "org.jetbrains.intellij.deps:intellij-core:223.0"
   }
   ```

2. **IDE에서 모듈 Classpath 확인**:
   - IntelliJ를 사용 중이라면 `Project Structure`에서 모듈의 `Dependencies` 섹션을 확인하여 필요한 라이브러리가 제대로 추가되어 있는지 확인하세요.
   - `Invalidate Caches / Restart` 옵션을 사용해 캐시 문제를 해결해 보세요.

3. **버전 충돌 해결**:
   `intellij-core`와 같은 라이브러리들이 서로 다른 버전으로 포함되어 있는 경우 충돌이 발생할 수 있습니다. 모든 관련 라이브러리의 버전을 맞추는 것이 중요합니다.

4. **라이브러리 추가**:
   만약 누락된 라이브러리로 인해 문제가 발생하는 것이라면, 해당 라이브러리를 명시적으로 종속성에 추가해 보세요.

위 방법들을 차례대로 시도해보면 문제가 해결될 가능성이 높습니다.

# Q2 - maven

# A2

Kotlin에서 `Disposer.newDisposable()`을 호출할 때 발생하는 "Can't access 'java.lang.Object'..." 오류는 주로 **Maven 종속성 충돌** 또는 **누락된 종속성**과 관련된 문제입니다. 다음 단계를 따라 문제를 해결할 수 있습니다.

### 1. **Maven 종속성 추가**
`Disposer` 클래스는 IntelliJ의 라이브러리(`intellij-core`)에 포함되어 있습니다. `pom.xml`에 해당 라이브러리를 추가해보세요.

```xml
<dependency>
    <groupId>org.jetbrains.intellij.deps</groupId>
    <artifactId>intellij-core</artifactId>
    <version>223.0</version> <!-- 최신 버전으로 설정하세요 -->
    <scope>provided</scope> <!-- 필요에 따라 compile로 변경 가능 -->
</dependency>
```

### 2. **종속성 충돌 확인**
Maven 프로젝트에서 라이브러리 간 충돌이 발생할 수 있습니다. 이를 확인하기 위해 `mvn dependency:tree` 명령을 실행하여 종속성 트리를 확인하세요.

```bash
mvn dependency:tree
```

이 명령을 실행하면 프로젝트에 포함된 모든 종속성이 출력됩니다. 여기서 **중복되거나 충돌하는 종속성**이 있는지 확인하세요. 만약 여러 버전의 라이브러리가 충돌하는 경우, `pom.xml`에서 특정 버전을 강제로 사용하도록 설정해야 합니다.

예시:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-stdlib</artifactId>
            <version>1.6.0</version> <!-- 프로젝트에서 사용하는 Kotlin 버전으로 설정 -->
        </dependency>
        <dependency>
            <groupId>org.jetbrains.intellij.deps</groupId>
            <artifactId>intellij-core</artifactId>
            <version>223.0</version> <!-- 충돌 해결을 위해 버전 통일 -->
        </dependency>
    </dependencies>
</dependencyManagement>
```

### 3. **Maven 재빌드**
`pom.xml` 수정 후, Maven을 다시 빌드하여 종속성을 다시 다운로드하도록 합니다.

```bash
mvn clean install
```

### 4. **라이브러리 범위 확인**
종속성의 `scope`가 올바르게 설정되어 있는지 확인하세요. 기본적으로 `provided`나 `compile`로 설정하면 됩니다.

```xml
<scope>compile</scope> <!-- 필요에 따라 provided에서 compile로 변경 -->
```

### 요약:
- `pom.xml`에 필요한 라이브러리가 포함되었는지 확인.
- `mvn dependency:tree` 명령으로 종속성 충돌 확인.
- 종속성 충돌이 있으면 버전 고정.
- Maven을 재빌드하여 변경 사항 반영.

이 과정을 통해 대부분의 종속성 관련 문제를 해결할 수 있습니다.