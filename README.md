# Router 的使用

## 添加注解

注解分两类：`RouterPath` 和 `RouterParam`，前者用于给类设置路由路径，后者用于参数自动注入。`RouterParam` 的 `key` 设置成 `Intent Bundle` 中 `key` 的值或者 `URL` 中 `Param key` 。注意如果要是用参数自动注入功能，**请务必调用 `Router.inject(object)` 方法** ，推荐在基类中调用。使用方法如下：

### Activity

```java
@RouterPath(path = "module/activity")
public class TestActivity extends BaseActivity {

    @RouterParam(key = "name_int")
    private int testInt = 100;

    @RouterParam(key = "name_string")
    String testString;

    ...

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Router.inject(this);
        ...
    }

    ...
}
```

### Fragment

```java
@RouterPath(path = "module/fragmet")
public class TestFragment extends Fragment {
    private static final String TAG = TestFragment.class.getSimpleName();

    @RouterParam(key = "name_int")
    private int testInt = 100;

    @RouterParam(key = "name_string")
    String testString;

    ...

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Router.inject(this);
        ...
    }

    ...
```

### ParamProvider

```java
@RouterPath(path = "trip/data")
public class TestService implements IParamProvider {
    ...
}
```

## Router 方法调用

**首先务必调用 `Router.init(context)` 方法**

### Activity 的跳转
```java
Router.toActivity(this, "module/activity");
```

### 带参数 Activity 的跳转
```java
Router.buildParams().withInt("int", 123)
                .toActivity(this, "module/activity");
```

### 获取 Fragment 实例
```java
Fragment fragment = (Fragment) Router.buildParams()
                .withInt("int", 123)
                .withString("string", "test")
                .getFragmentInstance("module/fragmet");
```

### 获取 Fragment class

```java
Class clazz = Router.getFragmentClass("module/fragmet");
```

## 跳转协议

使用前 **务必调用 `Router.configeProtocol(protocolParser)` 方法**，`protocolParser` 可以使用 `DefaultProtocolParser` ，细节可以查看该类的注释。

```java
Router.toProtocol(this, "crgt://photo_detail?photo_id=123&content=hahahahaha&testBoolean=true&testFloat=3.1415");
```


## 其他

### 拦截器使用

```java
Router.addInterceptor(new RouterInterceptor() {
            @Override
            public void intercept(Context context, String componentName, @Nullable ParamBuilder param, Callback callback) {
                if ("intercept_test".equals(componentName)) {
                    Log.d("RouterInterceptor","Activity was intercepted");
                    callback.onIntercept();
                } else {
                    callback.onContinue();
                }
            }
        });
```


### 自定义路由解析器
```java
//使用默认解析器  scheme://host/path?params
Router.configProtocol(new DefaultProtocolParser("crgt", DefaultProtocolParser.ComponentIdentifier.PATH));

```

### 配置路由出错处理器
```java
Router.setNotFoundHandler(new RouterNotFoundHandler() {
            @Override
            public void noActivityFound(Context appContext, String path, String errorMsg) {
                Toast.makeText(appContext, "Custom msg: " + path + " not found", Toast.LENGTH_SHORT).show();
            }
        });
```

### 欢迎补充，修正错误