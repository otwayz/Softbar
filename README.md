# Softbar
Statusbar   dark &amp; light mode  change

# 用法

```bash
compile 'com.zgc:softbar-release:1.0.0'
```

```java
public class BaseActivity extend Activity {
  	
    public void statusBarModeChange() {
        SoftBar.with(this).safeDarkFont(Color.WHITE);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.xxx);
        
        statusBarModeChange();
    }
    
    @Override
    protected void onDestroy() {
        SoftBar.release(this);
        
    		super.onDestroy();
    }
    
}
```

实现沉浸式 和  电池所在状态栏字体变色。
支持小米 魅族 5.0及以上系统
支持 Android 6.0及以上系统

![浅色模式](https://github.com/otwayz/Softbar/blob/HEAD/imge/light.png)


![深色模式](https://github.com/otwayz/Softbar/blob/HEAD/imge/dark.png)


![沉浸模式](https://github.com/otwayz/Softbar/blob/HEAD/imge/immerse.jpeg)
