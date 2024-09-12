`kotlinx-dataframe` 라이브러리를 Maven에서 제대로 가져오지 못하는 경우는 몇 가지 이유가 있을 수 있습니다. 해결책을 시도하기 위해 몇 가지 방법을 안내드리겠습니다.

### 1. 올바른 저장소 추가

Maven 프로젝트에서 `kotlinx-dataframe`을 사용하려면 `mavenCentral()` 저장소에서 찾을 수 있도록 `pom.xml`에 저장소 정보를 추가해야 할 수 있습니다. Maven Central 저장소를 명시적으로 추가하는 방법은 다음과 같습니다:

```xml
<repositories>
    <repository>
        <id>central</id>
        <url>https://repo.maven.apache.org/maven2</url>
    </repository>
</repositories>
```

이 부분을 `pom.xml`의 `<repositories>` 섹션에 추가하세요.  

### 2. 정확한 의존성 확인

Maven에서 `kotlinx-dataframe`의 의존성이 제대로 설정되지 않았을 수 있습니다. 최신 버전의 `kotlinx-dataframe`이 Maven Central에 배포되지 않았거나 의존성 설정이 잘못되었을 수 있습니다.

따라서 Maven Central에 등록된 최신 버전의 `kotlinx-dataframe` 의존성을 사용하는 것이 중요합니다. 다음처럼 의존성을 추가할 수 있습니다.

```xml
<dependency>
    <groupId>org.jetbrains.kotlinx</groupId>
    <artifactId>dataframe</artifactId>
    <version>0.9.0</version> <!-- 현재 시점의 최신 버전 확인 필요 -->
</dependency>
```

`<version>`에 들어갈 최신 버전은 [Maven Central](https://search.maven.org/)에서 확인할 수 있습니다.

### 3. Kotlin 플러그인과 버전 호환성 확인

Maven에서 Kotlin을 사용할 때는 `kotlin-maven-plugin`이 제대로 설정되어 있어야 합니다. 이미 Maven 설정에서 `kotlin-maven-plugin`을 추가했지만, Kotlin 버전과의 호환성을 다시 한번 확인해보세요. 

예시로, `kotlin.version`을 1.8.0 이상으로 설정하는 것을 추천드립니다:

```xml
<properties>
    <kotlin.version>1.8.0</kotlin.version>
</properties>
```

### 최종적으로 수정된 `pom.xml` 예시

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>kotlin-dataframe-example</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <kotlin.version>1.8.0</kotlin.version> <!-- Kotlin 버전 -->
        <dataframe.version>0.9.0</dataframe.version> <!-- kotlinx-dataframe 버전 -->
    </properties>

    <dependencies>
        <!-- Kotlin Standard Library -->
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-stdlib</artifactId>
            <version>${kotlin.version}</version>
        </dependency>

        <!-- kotlinx-dataframe -->
        <dependency>
            <groupId>org.jetbrains.kotlinx</groupId>
            <artifactId>dataframe</artifactId>
            <version>${dataframe.version}</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Kotlin Maven Plugin -->
            <plugin>
                <groupId>org.jetbrains.kotlin</groupId>
                <artifactId>kotlin-maven-plugin</artifactId>
                <version>${kotlin.version}</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>compile</goal>
                            <goal>test-compile</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

    <repositories>
        <repository>
            <id>central</id>
            <url>https://repo.maven.apache.org/maven2</url>
        </repository>
    </repositories>
</project>
```

이렇게 설정하고 다시 빌드를 시도하면 `kotlinx-dataframe` 라이브러리가 정상적으로 가져와질 것입니다.


# Maven

`kotlinx-dataframe`은 Gradle에서 주로 사용되지만, Maven에서도 사용할 수 있습니다. Maven 프로젝트에서 Kotlin DataFrame 라이브러리를 사용하려면, `pom.xml` 파일에 다음과 같은 의존성을 추가하면 됩니다.

먼저, `pom.xml` 파일에 Kotlin과 관련된 플러그인과 라이브러리를 추가하세요.

### Maven 설정 (pom.xml)

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>kotlin-dataframe-example</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <kotlin.version>1.8.0</kotlin.version> <!-- Kotlin 버전 -->
        <dataframe.version>0.8.0</dataframe.version> <!-- kotlinx-dataframe 버전 -->
    </properties>

    <dependencies>
        <!-- Kotlin Standard Library -->
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-stdlib</artifactId>
            <version>${kotlin.version}</version>
        </dependency>

        <!-- kotlinx-dataframe -->
        <dependency>
            <groupId>org.jetbrains.kotlinx</groupId>
            <artifactId>dataframe</artifactId>
            <version>${dataframe.version}</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Kotlin Maven Plugin -->
            <plugin>
                <groupId>org.jetbrains.kotlin</groupId>
                <artifactId>kotlin-maven-plugin</artifactId>
                <version>${kotlin.version}</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>compile</goal>
                            <goal>test-compile</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

이 설정으로 `kotlinx-dataframe` 라이브러리를 Maven 프로젝트에서 사용할 수 있습니다. 라이브러리와의 의존성 관리는 Gradle뿐 아니라 Maven에서도 문제없이 동작합니다.

이후에 동일한 코드를 사용하여 데이터프레임을 만들고, 새로운 row를 추가할 수 있습니다. `kotlinx-dataframe`은 특정 빌드 도구와는 무관하게 Kotlin 코드 내에서 동작합니다.

### 데이터프레임 생성 및 row 추가 코드 (Maven에서도 동일하게 동작)

```kotlin
import org.jetbrains.kotlinx.dataframe.api.*

fun main() {
    // 기존 데이터 프레임 생성
    val df = dataFrameOf(
        "name", "age", "school"
    )(
        "Alice", 20, "High School",
        "Bob", 22, "University",
        "Charlie", 19, "High School"
    )

    // 새로운 row 추가
    val newRow = mapOf(
        "name" to "David",
        "age" to 23,
        "school" to "Graduate School"
    )
    
    val updatedDf = df.append(newRow)

    // 업데이트된 데이터 프레임 출력
    println(updatedDf)
}
```

따라서 Maven을 사용해서도 동일하게 Kotlin DataFrame을 다룰 수 있으며, 새로운 row 추가 기능 역시 문제없이 작동합니다.

# Add row
`kotlinx-dataframe`을 사용해서 기존 데이터프레임에 새로운 row를 추가하는 방법은 `append` 함수를 사용하는 것입니다. 예를 들어, 위에서 생성한 데이터프레임에 새로운 row를 추가하는 코드는 다음과 같습니다:

```kotlin
import org.jetbrains.kotlinx.dataframe.api.*

fun main() {
    // 기존 데이터 프레임 생성
    val df = dataFrameOf(
        "name", "age", "school"
    )(
        "Alice", 20, "High School",
        "Bob", 22, "University",
        "Charlie", 19, "High School"
    )

    // 새로운 row 추가
    val newRow = mapOf(
        "name" to "David",
        "age" to 23,
        "school" to "Graduate School"
    )
    
    val updatedDf = df.append(newRow)

    // 업데이트된 데이터 프레임 출력
    println(updatedDf)
}
```

위 코드에서 `append` 메서드를 사용해 새로운 row를 추가합니다. 새로운 row는 `mapOf`를 사용하여 컬럼 이름과 값을 매핑한 후 추가할 수 있습니다. `updatedDf`는 새로운 row가 포함된 데이터 프레임입니다.

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