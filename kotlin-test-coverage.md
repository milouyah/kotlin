Maven 기반의 Kotlin 프로젝트에서 테스트 커버리지를 측정하려면 **JaCoCo**(Java Code Coverage) 플러그인이 일반적으로 사용됩니다. JaCoCo는 Kotlin 코드에도 잘 적용되며, Maven과 쉽게 통합할 수 있습니다.

JaCoCo 설정을 Maven 프로젝트에 추가하려면 `pom.xml` 파일에 JaCoCo 플러그인을 추가하면 됩니다. 기본적인 설정 예시는 다음과 같습니다:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.jacoco</groupId>
            <artifactId>jacoco-maven-plugin</artifactId>
            <version>0.8.8</version> <!-- 최신 버전을 사용 -->
            <executions>
                <execution>
                    <goals>
                        <goal>prepare-agent</goal>
                    </goals>
                </execution>
                <execution>
                    <id>report</id>
                    <phase>test</phase>
                    <goals>
                        <goal>report</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

위 설정은 테스트 실행 시 JaCoCo가 코드를 분석하고, `target/site/jacoco` 디렉토리에 HTML 형태의 커버리지 리포트를 생성합니다.

### 커버리지 리포트 생성 방법:
1. **Maven 명령어 실행**: `mvn clean test`  
   이 명령어를 실행하면 테스트가 실행되면서 JaCoCo가 커버리지를 수집합니다.

2. **리포트 확인**: `target/site/jacoco/index.html` 파일을 브라우저에서 열어 커버리지 리포트를 확인할 수 있습니다.

추가적으로, Kotlin 프로젝트에 대한 커버리지 측정을 정확히 하기 위해 JaCoCo 외에도 Kotlin 특화된 플러그인이나 도구를 병행할 수 있습니다.