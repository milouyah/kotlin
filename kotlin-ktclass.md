`KtClass`에서 클래스 이름을 가져오려면 `getName()` 메서드를 사용하면 됩니다. 이 메서드는 클래스의 이름을 문자열로 반환합니다.

다음은 예시 코드입니다:

```kotlin
import org.jetbrains.kotlin.psi.KtClass

fun getClassName(ktClass: KtClass): String? {
    // 클래스 이름을 가져오기
    return ktClass.name
}
```

### 추가 설명:
- `ktClass.name`: 클래스의 이름을 반환하는 프로퍼티입니다. 클래스가 익명 클래스일 경우 `null`을 반환할 수 있으니 `null` 체크를 해야 합니다.

이 코드를 사용하면 `KtClass` 객체로부터 클래스 이름을 쉽게 가져올 수 있습니다.