import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRoot
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.config.CommonConfigurationKeys
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.psi.*
import java.io.File

fun main() {
    val disposable = Disposer.newDisposable()

    // 1. Kotlin 파일을 읽음
    val fileContent = File("src/main/kotlin/KotlinParser.kt").readText()

    // 2. 파서 환경을 설정
    val configuration = CompilerConfiguration().apply {
        put(CommonConfigurationKeys.MODULE_NAME, "example")
        add(CLIConfigurationKeys.CONTENT_ROOTS,JvmClasspathRoot(File("")))
    }

    // 3. 파일을 파싱하여 AST 생성

    val environment = KotlinCoreEnvironment.createForProduction(
        disposable, configuration, EnvironmentConfigFiles.JVM_CONFIG_FILES
    )

    // 4. 파싱된 파일을 처리
    val psiFactory = KtPsiFactory(environment.project)
    val ktFile: KtFile = psiFactory.createFile(fileContent)

    // 5. AST의 내용을 출력
    ktFile.accept(object : KtVisitorVoid() {
        override fun visitKtElement(element: org.jetbrains.kotlin.psi.KtElement) {
            println("Element: ${element.text}")
            super.visitKtElement(element)
        }

        override fun visitKtFile(ktFile: KtFile) {
            println("KtFile: ${ktFile.text}")
            super.visitKtFile(ktFile)
        }
    })

    ktFile.acceptChildren(object : KtVisitorVoid() {
        override fun visitKtElement(element: org.jetbrains.kotlin.psi.KtElement) {
            println("Element: ${element.text}")
            super.visitKtElement(element)
        }

        override fun visitKtFile(ktFile: KtFile) {
            println("KtFile: ${ktFile.text}")
            super.visitKtFile(ktFile)
        }
    })


    // 하위 요소 탐색 (acceptChildren 사용)
    ktFile.acceptChildren(object : KtVisitorVoid() {
        override fun visitElement(element: PsiElement) {
            println("Element: ${element::class.simpleName} -> ${element.text}")
            super.visitElement(element)
        }

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