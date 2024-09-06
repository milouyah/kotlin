Maven에서 기본 중앙 저장소 외에 다른 저장소를 추가하려면 `pom.xml` 파일에 `<repositories>` 태그를 사용하여 저장소를 정의할 수 있습니다. 아래는 추가 저장소를 설정하는 예시입니다:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">

    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>my-app</artifactId>
    <version>1.0-SNAPSHOT</version>

    <!-- repositories 태그를 통해 추가 저장소 설정 -->
    <repositories>
        <repository>
            <id>my-repo</id>
            <url>https://my-repo-url.com/repository</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>

</project>
```

위 예시에서:

- `<id>`는 저장소의 식별자입니다.
- `<url>`은 추가할 저장소의 URL입니다.
- `<releases>` 및 `<snapshots>` 태그로 각각 릴리즈 및 스냅샷 아티팩트를 활성화하거나 비활성화할 수 있습니다.

이렇게 추가하면 Maven은 기본 중앙 저장소 외에도 정의된 저장소에서 아티팩트를 검색하게 됩니다.