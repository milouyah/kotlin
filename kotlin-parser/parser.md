Kotlin 컴파일러(Kotlin Compiler)를 사용하여 클래스와 메서드를 추출하는 방법을 설명드리겠습니다. Kotlin 컴파일러 API를 사용하면 Kotlin 파일을 구문 분석하고 필요한 정보를 추출할 수 있습니다. 여기서는 `Kotlin Compiler`와 `Kotlin Parser`를 사용하는 기본적인 방법을 소개하겠습니다.

### 1. Kotlin Compiler API 설정

먼저 Gradle을 통해 Kotlin Compiler API를 사용해야 합니다. 프로젝트의 `build.gradle.kts` 파일에 다음 종속성을 추가합니다:

```kotlin
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.0")
}
```

### 2. Kotlin 파일 파싱 및 메서드 추출

이제 Kotlin Compiler API를 사용하여 Kotlin 파일을 구문 분석하고 클래스와 메서드를 추출하는 방법을 알아보겠습니다.

다음은 예시 코드입니다:

```kotlin
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRoot
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CommonConfigurationKeys
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.com.intellij.core.JavaCoreApplicationEnvironment
import org.jetbrains.kotlin.com.intellij.openapi.project.Project
import org.jetbrains.kotlin.psi.KtVisitorVoid

fun main() {
    // Kotlin 파일 경로 설정
    val filePath = "src/main/kotlin/YourKotlinFile.kt"
    
    // Kotlin Compiler 환경 설정
    val configuration = CompilerConfiguration().apply {
        put(CommonConfigurationKeys.MODULE_NAME, "example")
        add(CLIConfigurationKeys.CONTENT_ROOTS, JvmClasspathRoot(File("path/to/your/classpath")))
    }
    
    val disposable = Disposer.newDisposable()
    val environment = KotlinCoreEnvironment.createForProduction(
        disposable,
        configuration,
        EnvironmentConfigFiles.JVM_CONFIG_FILES
    )
    
    val project: Project = environment.project
    val psiFile = KotlinCompilerUtils.createPsiFile(filePath, project)

    // Kotlin 파일을 KtFile로 변환
    val ktFile = psiFile as? KtFile ?: throw IllegalArgumentException("Invalid Kotlin file")

    // 클래스와 메서드 추출
    ktFile.accept(object : KtVisitorVoid() {
        override fun visitClass(klass: KtClass) {
            println("Class: ${klass.name}")
            super.visitClass(klass)
        }

        override fun visitNamedFunction(function: KtNamedFunction) {
            println("Method: ${function.name}")
            super.visitNamedFunction(function)
        }
    })

    Disposer.dispose(disposable)
}
```

### 설명:

1. **Kotlin Compiler 환경 설정**: `CompilerConfiguration` 클래스를 사용하여 Kotlin 컴파일러의 설정을 구성합니다.
2. **KtFile 변환**: 파일 경로를 기반으로 Kotlin 파일을 `KtFile`로 변환합니다.
3. **클래스와 메서드 추출**: `KtVisitorVoid`를 사용하여 Kotlin 파일을 순회하면서 클래스(`KtClass`)와 메서드(`KtNamedFunction`)를 추출합니다.

### 3. 실행 방법

이 코드를 프로젝트 내에서 실행하면, 지정한 Kotlin 파일에서 클래스와 메서드 이름을 출력하게 됩니다. 

위 방식으로 `Kotlin Compiler`를 활용하여 Kotlin 소스 파일을 구문 분석하고 필요한 정보를 추출할 수 있습니다.