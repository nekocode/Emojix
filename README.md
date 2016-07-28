# Emojix
[![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html) [![Release](https://img.shields.io/github/release/nekocode/Emojix.svg?label=Jitpack)](https://jitpack.io/#nekocode/Emojix)

### Preview
![preview](art/preview.png)

### Using with gradle
- Add the JitPack repository to your root build.gradle:
```gradle
repositories {
    maven { url "https://jitpack.io" }
}
```

- Add the dependency to your sub build.gradle:
```gradle
dependencies {
    compile 'com.github.nekocode:Emojix:{lastest-version}'
}
```

### Usage

Just wrap the `Activity` Context:

```java
@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
}
```

And everything is OK now! Anyway, you can apply these code to your `BaseActivity` for implementing emojis to the whole application.