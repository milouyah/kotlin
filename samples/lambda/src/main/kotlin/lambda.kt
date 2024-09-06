

fun simple_lambda(){
    val company = {println("GeeksforGeeks")}

    // invoking function method1
    company()

    // invoking function method2
    company.invoke()

}

fun two_args_lambda(){
    // with type annotation in lambda expression
    val sum1 = { a: Int, b: Int -> a + b }

    // without type annotation in lambda expression
    val sum2:(Int,Int)-> Int  = { a , b -> a + b}

    val result1 = sum1(2,3)
    val result2 = sum2(3,4)
    println("The sum of two numbers is: $result1")
    println("The sum of two numbers is: $result2")

    // directly print the return value of lambda
    // without storing in a variable.
    println(sum1(5,7))
}

fun shorthand_lambda(){
    val numbers = arrayOf(1,-2,3,-4,5)
    println(numbers.filter { it > 0 })
}

fun main(args: Array<String>){
    simple_lambda()
    two_args_lambda()

}