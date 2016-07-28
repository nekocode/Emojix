# Emojix
[![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html) [![Release](https://img.shields.io/github/release/nekocode/Emojix.svg?label=Jitpack)](https://jitpack.io/#nekocode/Emojix)

Use **[apple style emojis](http://unicode.org/emoji/charts/full-emoji-list.html)** on your android application the easiest way.

Something you need to know is that it will increase your apk size about 5 MB because it contains all emoji icon files.

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

Just wrap the `Activity` Context, it will replace all the emoji characters with apple style icons in all `TextView`:

```java
@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
}
```

Now, everything is OK! 

Anyway, you can also apply these code to your `BaseActivity` for implementing emoji to the whole application.

### Thanks

Thanks to the [emojicon](https://github.com/rockerhieu/emojicon) and [Calligraphy](https://github.com/chrisjenx/Calligraphy) project. This project is based on them.