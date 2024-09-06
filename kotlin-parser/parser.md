`KotlinToJVMBytecodeCompiler.parsePsiFile()`은 실제로 존재하지 않으며, Kotlin 컴파일러 API에서 직접적으로 PSI 파일을 가져오는 방식이 조금 더 복잡할 수 있습니다. 이를 해결하기 위해 IntelliJ 플랫폼을 기반으로 한 Kotlin PSI를 사용하는 방법을 다시 설명드리겠습니다.

### Kotlin PSI를 사용한 클래스 및 메서드 추출 예시

Kotlin 파일을 파싱하고 클래스와 메서드를 추출하려면 Kotlin PSI(Program Structure Interface)를 사용하는 것이 기본적입니다. IntelliJ 플랫폼이 제공하는 PSI와 Kotlin을 함께 사용할 수 있는 환경을 설정하고 사용하는 방법을 단계적으로 설명드리겠습니다.

### 1. Gradle 의존성 설정

먼저 Kotlin Compiler Embeddable과 IntelliJ Core를 추가해야 합니다.

```kotlin
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.0")
    implementation("org.jetbrains.intellij.deps:trove4j:1.0.20200330")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
    implementation("org.jetbrains.kotlin:kotlin-compiler:1.9.0")
    implementation("org.jetbrains.kotlin:kotlin-script-util:1.9.0")
    implementation("com.jetbrains.intellij.idea:ideaIC:2022.1")
}
```

### 2. Kotlin 파일 파싱 및 클래스와 메서드 추출

다음은 IntelliJ Core와 Kotlin Compiler를 활용하여 Kotlin 파일을 파싱하고 클래스 및 메서드를 추출하는 코드입니다.

```kotlin
import com.intellij.core.JavaCoreApplicationEnvironment
import com.intellij.openapi.util.Disposer
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiFileFactory
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRoot
import org.jetbrains.kotlin.cli.jvm.config.addJvmClasspathRoots
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CommonConfigurationKeys
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtVisitorVoid
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtNamedFunction
import java.io.File

fun main() {
    // Kotlin 파일 경로 설정
    val filePath = "src/main/kotlin/YourKotlinFile.kt"
    val kotlinFile = File(filePath).readText()

    // Disposable 생성
    val disposable = Disposer.newDisposable()

    // Kotlin 환경 설정
    val configuration = CompilerConfiguration().apply {
        put(CommonConfigurationKeys.MODULE_NAME, "example")
        addJvmClasspathRoots(listOf(File("path/to/your/classpath")))
    }

    // IntelliJ Core Application 환경 설정
    val environment = JavaCoreApplicationEnvironment(disposable)
    val project = environment.project
    val psiFileFactory = PsiFileFactory.getInstance(project)

    // Kotlin 파일을 PSI 파일로 변환
    val psiFile = psiFileFactory.createFileFromText("YourKotlinFile.kt", KtFile::class.java, kotlinFile) as KtFile

    // 클래스 및 메서드 추출
    psiFile.accept(object : KtVisitorVoid() {
        override fun visitClass(klass: KtClass) {
            println("Class: ${klass.name}")
            super.visitClass(klass)
        }

        override fun visitNamedFunction(function: KtNamedFunction) {
            println("Method: ${function.name}")
            super.visitNamedFunction(function)
        }
    })

    // 메모리 해제
    Disposer.dispose(disposable)
}
```

### 3. 설명

1. **환경 설정**: 
   - `CompilerConfiguration`을 통해 Kotlin 컴파일러의 설정을 구성합니다.
   - IntelliJ Core Application Environment를 사용하여 IntelliJ 기반의 PSI 환경을 설정합니다.
2. **PSI 파일 생성**: 
   - `PsiFileFactory`를 통해 `KtFile`을 생성하고, Kotlin 파일을 읽어들여 PSI 파일로 변환합니다.
3. **클래스 및 메서드 추출**: 
   - `KtVisitorVoid`를 사용하여 `KtFile` 내부의 클래스(`KtClass`)와 메서드(`KtNamedFunction`)를 탐색하고 출력합니다.

### 4. 실행 방법

1. `src/main/kotlin/YourKotlinFile.kt`에 분석할 Kotlin 파일을 준비합니다.
2. 위 코드를 실행하면 파일 내의 클래스와 메서드 이름이 출력됩니다.

이 방식은 IntelliJ의 PSI 시스템을 활용하여 Kotlin 파일을 분석할 수 있는 안정적인 방법입니다. 이 코드를 통해 원하는 정보를 추출할 수 있을 것입니다.

`KotlinCompilerUtils`는 Kotlin 컴파일러 API에서 제공하지 않는 클래스입니다. 따라서 이를 사용하지 않고 다른 방법으로 Kotlin 파일을 파싱하는 방법을 제공하겠습니다.

### Kotlin 파일을 파싱하고 클래스 및 메서드를 추출하는 방법 (Kotlin Compiler API 사용)

대신 IntelliJ의 PSI (Program Structure Interface)와 Kotlin Compiler API를 사용하여 Kotlin 파일을 파싱하는 방법을 제공하겠습니다. 다음은 Kotlin Compiler API와 Kotlin PSI를 사용하여 클래스를 파싱하는 방법입니다.

### 1. Gradle 의존성 설정

```kotlin
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
}
```

### 2. Kotlin 파일 파싱 예시 코드

다음은 Kotlin Compiler API 및 PSI를 이용하여 Kotlin 파일을 파싱하고 클래스 및 메서드를 추출하는 방법입니다.

```kotlin
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.jvm.JvmCliInitializer
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRoot
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootsKt
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CommonConfigurationKeys
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtVisitorVoid
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes
import java.io.File

fun main() {
    // Kotlin 파일 경로 설정
    val filePath = "src/main/kotlin/YourKotlinFile.kt"
    val file = File(filePath)

    // Kotlin Compiler 환경 설정
    val configuration = CompilerConfiguration().apply {
        put(CommonConfigurationKeys.MODULE_NAME, "example-module")
        add(CLIConfigurationKeys.CONTENT_ROOTS, JvmClasspathRoot(File("src/main/kotlin")))
    }

    // Disposable 객체 생성
    val disposable = Disposer.newDisposable()

    // 환경 설정으로 KotlinCoreEnvironment 생성
    val environment = KotlinCoreEnvironment.createForProduction(
        disposable,
        configuration,
        JvmCliInitializer.JVM_CONFIG_FILES
    )

    // Kotlin 파일을 파싱
    val psiFile = KotlinToJVMBytecodeCompiler.parsePsiFile(file, environment) as KtFile

    // 클래스와 메서드 추출
    psiFile.accept(object : KtVisitorVoid() {
        override fun visitClass(klass: KtClass) {
            println("Class: ${klass.name}")
            super.visitClass(klass)
        }

        override fun visitNamedFunction(function: KtNamedFunction) {
            println("Method: ${function.name}")
            super.visitNamedFunction(function)
        }
    })

    // 메모리 해제
    Disposer.dispose(disposable)
}
```

### 3. 설명

1. **환경 설정**: `CompilerConfiguration`을 사용하여 Kotlin 파일을 파싱하기 위한 환경을 구성합니다.
2. **파일 파싱**: `KotlinToJVMBytecodeCompiler.parsePsiFile()`을 사용하여 `.kt` 파일을 `KtFile`로 파싱합니다.
3. **클래스 및 메서드 추출**: `KtVisitorVoid`를 사용하여 파싱된 파일 내의 클래스와 메서드를 탐색하고 출력합니다.

### 4. 실행 방법

1. `src/main/kotlin/YourKotlinFile.kt` 경로에 파싱할 Kotlin 파일을 준비합니다.
2. 코드를 실행하면, 해당 파일 내의 모든 클래스와 메서드 이름이 출력됩니다.

이 방법을 사용하면 Kotlin 파일을 파싱하고 원하는 정보를 추출할 수 있습니다.


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