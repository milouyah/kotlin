`KtFile`에서 패키지 이름을 가져오는 방법은 `KtFile` 객체의 `packageFqName` 프로퍼티를 사용하면 됩니다. 이 프로퍼티는 해당 파일의 패키지 명을 `FqName` 형태로 반환합니다. 이를 통해 간단하게 패키지 이름을 문자열로 얻을 수 있습니다.

예시 코드는 다음과 같습니다:

```kotlin
import org.jetbrains.kotlin.psi.KtFile

fun getPackageName(ktFile: KtFile): String {
    // packageFqName은 FqName 객체이므로, .asString()을 통해 문자열로 변환
    return ktFile.packageFqName.asString()
}
```

이 코드를 사용하면 `KtFile` 객체로부터 패키지 이름을 가져와 문자열로 변환할 수 있습니다.

### 추가 설명:
- `packageFqName`: 이 속성은 파일의 완전한 패키지 이름(`Fully Qualified Name`)을 반환하는데, 이는 `FqName` 객체로 제공됩니다.
- `.asString()`: `FqName` 객체를 문자열로 변환하는 메서드입니다.

이렇게 하면 원하는 패키지 이름을 쉽게 얻을 수 있습니다.