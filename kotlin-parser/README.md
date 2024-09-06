# Kotlin Parser

## Kotlin Parser Dependencies

```xml
<!-- https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-compiler-embeddable -->
<dependency>
    <groupId>org.jetbrains.kotlin</groupId>
    <artifactId>kotlin-compiler-embeddable</artifactId>
    <version>2.0.20</version>
</dependency>
```

## Tree 구조

```java
    // PSI 트리 구조를 출력하는 함수
    fun printPsiTree(element: KtElement, indent: String = "") {
        println("$indent${element::class.simpleName}: ${element.text}")
        element.acceptChildren(object : KtVisitorVoid() {
            override fun visitElement(element: KtElement) {
                printPsiTree(element, "$indent  ")
            }
        })
    }

    // 트리 출력
    printPsiTree(psiFile)
```

## [Kotlin Compiler References](./kotlin-compiler-references.md)