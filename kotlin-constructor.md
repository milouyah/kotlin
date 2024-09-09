Kotlin에서 생성자(constructor)를 정의하는 방법은 두 가지가 있습니다: **주 생성자(primary constructor)**와 **보조 생성자(secondary constructor)**. 주 생성자는 클래스 헤더에 직접 정의되고, 보조 생성자는 클래스 본문 내부에 정의됩니다.

### 1. **주 생성자 (Primary Constructor)**

주 생성자는 클래스 이름 옆에 직접 정의되며, 가장 간단한 형태의 생성자입니다. 파라미터를 받아 클래스의 프로퍼티를 초기화할 수 있습니다.

```kotlin
class Person(val name: String, var age: Int)
```

위 예시에서 `name`과 `age`는 주 생성자의 파라미터이며, `val`과 `var`로 선언되어 클래스 프로퍼티가 됩니다.

#### 추가 설명:
- **`val`** 또는 **`var`**를 사용하면, 파라미터가 클래스의 멤버 변수로 자동으로 선언됩니다.
- 주 생성자에서 추가적인 로직을 실행하고 싶다면 `init` 블록을 사용할 수 있습니다.

```kotlin
class Person(val name: String, var age: Int) {
    init {
        println("Person's name is $name and age is $age")
    }
}
```

#### 기본값을 가진 생성자:

```kotlin
class Person(val name: String = "Unknown", var age: Int = 0)
```

위 코드에서는 `name`과 `age`에 기본값을 지정할 수 있습니다.

---

### 2. **보조 생성자 (Secondary Constructor)**

보조 생성자는 `constructor` 키워드를 사용하여 클래스 본문에 정의합니다. 보조 생성자는 주 생성자를 호출하거나, 다른 보조 생성자를 호출할 수 있습니다.

```kotlin
class Person {
    var name: String
    var age: Int

    // 보조 생성자
    constructor(name: String, age: Int) {
        this.name = name
        this.age = age
    }
}
```

보조 생성자를 사용하면 주 생성자와 다르게 추가적인 로직을 넣을 수 있으며, 기본적으로는 주 생성자를 호출하여 코드를 단순화할 수 있습니다.

#### 주 생성자를 가진 클래스에서 보조 생성자 정의:

```kotlin
class Person(val name: String) {

    var age: Int = 0

    // 보조 생성자
    constructor(name: String, age: Int) : this(name) {
        this.age = age
    }
}
```

#### 설명:
- `: this(name)`는 보조 생성자가 주 생성자를 호출하는 부분입니다. 보조 생성자는 반드시 주 생성자를 호출해야 합니다.
- 이 방법으로 주 생성자와 보조 생성자를 혼합하여 사용할 수 있습니다.

---

### 3. **디폴트 생성자 (No-argument Constructor)**

만약 클래스에 생성자가 따로 없으면, Kotlin은 기본적으로 파라미터가 없는 디폴트 생성자를 제공합니다.

```kotlin
class EmptyClass

// 위 클래스는 아무런 생성자도 정의하지 않았지만, 디폴트 생성자가 존재합니다.
val obj = EmptyClass()  // 디폴트 생성자 호출
```

---

### 4. **예제**: 주 생성자와 보조 생성자 모두 사용하기

```kotlin
class Car(val brand: String, var model: String) {
    
    var year: Int = 0

    // 보조 생성자
    constructor(brand: String, model: String, year: Int) : this(brand, model) {
        this.year = year
    }

    fun printInfo() {
        println("Car: $brand $model, Year: $year")
    }
}

fun main() {
    val car1 = Car("Toyota", "Corolla")  // 주 생성자 호출
    val car2 = Car("Honda", "Civic", 2022)  // 보조 생성자 호출
    
    car1.printInfo()  // 출력: Car: Toyota Corolla, Year: 0
    car2.printInfo()  // 출력: Car: Honda Civic, Year: 2022
}
```

### 요약:
- **주 생성자 (Primary Constructor)**는 클래스 헤더에서 정의되며, 간결하고 클래스의 프로퍼티를 초기화할 수 있습니다.
- **보조 생성자 (Secondary Constructor)**는 클래스 본문에 정의되며, 주 생성자를 호출하거나 추가적인 초기화 로직을 포함할 수 있습니다.
- **`init` 블록**은 주 생성자의 로직을 처리하기 위한 블록입니다.
- **기본값**을 사용하여 생성자의 파라미터에 기본 값을 제공할 수 있습니다.