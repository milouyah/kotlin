# Kotlin Lambda Example

## What is Lambda
Lambdas expression and Anonymous function both are function literals means 
these functions are not declared but passed immediately as an expression.

```kotlin
val lambda_name : Data_type = { argument_List -> code_body }
```

A lambda expression is always surrounded by curly braces, argument declarations go inside curly braces and have optional type annotations, the code_body goes after an arrow -> sign. If the inferred return type of the lambda is not Unit, then the last expression inside the lambda body is treated as return value.

Lambdas can be used as class extension:
```kotlin
val lambda4: String.(Int) -> String = {this + it}
```
 
Here, it represents the implicit name of single parameter and we will discuss later.

## Links

* [Kotlin | Lambdas Expressions and Anonymous Functions](https://www.geeksforgeeks.org/kotlin-lambdas-expressions-and-anonymous-functions/#:~:text=If%20the%20inferred%20return%20type%20of%20the%20lambda,the%20lambda%20expression%20after%20eliminating%20the%20optional%20part.)
