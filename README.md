# StatusView 多功能状态控件  
简单实用的页面多状态布局(content,loading,empty,error)  
 
[GitHub 地址](https://github.com/LZHS/StatusView)，效果图如下： <br/> 

<img src="https://github.com/ThirteenKilometers/GuardianLife/blob/master/Images/Screenshot_20180206-145823-156.jpg?raw=true" width = 40% height = 40% />

<img src="https://github.com/ThirteenKilometers/GuardianLife/blob/master/Images/Screenshot_20180206-145457-429.jpg?raw=true" width = 40% height = 40% />

<img src="https://github.com/ThirteenKilometers/GuardianLife/blob/master/Images/Screenshot_20180206-145509-817.jpg?raw=true" width = 40% height = 40% />

<img src="https://github.com/ThirteenKilometers/GuardianLife/blob/master/Images/Screenshot_20180206-145517-721.jpg?raw=true" width = 40% height = 40% />

## Gradle
[![](https://jitpack.io/v/LZHS/StatusView.svg)](https://jitpack.io/#LZHS/StatusView)

``` groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
dependencies {
	        compile 'com.github.LZHS:StatusView:v1.0.1'
	}
```
    
## Usage 

**在主题中设置默认样式**

``` xml  
	<style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="android:textColorSecondary">#ffffff</item>
        <item name="windowNoTitle">true</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        
        <item name="StatusViewAttr">@style/CurssStatusStyle</item> 
    </style> 
    
    <style name="CurssStatusStyle" parent="StatusView.Style">
        <item name="StatusTextColor">#99999</item>
        <item name="StatusTextSize">13sp</item> 
        <item name="StatusButtonTextColor">#999999</item>
        <item name="StatusButtonTextSize">13sp</item>
        <item name="StatusButtonBackground">@drawable/status_loading_layout_button</item>

        <item name="StatusEmptyImage">@mipmap/empty</item>
        <item name="StatusEmptyText">暂无数据</item>
        <item name="StatusErrorImage">@mipmap/error</item>
        <item name="StatusErrorText">无网络连接，请检查您的网络...</item>
        <item name="StatusRetryText">加载失败，点击重试~~</item>
        <item name="StatusLoadingText">正在加载...</item>
    </style>

```

**用法一：在布局中使用**

``` xml  
 <lzhs.com.StatusView
        android:id="@+id/mStatusView"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:gravity="center"
            android:text="这个是内容视图" />
    </lzhs.com.StatusView>
 
``` 

**用法二：包裹并替换内容元素**

``` java   
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    // ...
    
    mStatusView = StatusView.wrap(this); 
    mStatusView.showContent(); 
    
    // ...
}
``` 
**API**

``` java
// 显示 content/loading/empty/error 布局
showContent()
showLoading()
showEmpty()
showError()

// 设置 loading/empty/error 布局
setLoading(int resId)
setEmpty(int resId)
setError(int resId)

// 设置空布局的图片与文本
setEmptyImage(int resId)
setEmptyText(String value)

// 设置错误布局的图片与文本，
setErrorImage(int resId)
setErrorText(String value) 

// 设置重试按钮文本
setRetryText(String value)

// 设置重试按钮的监听回调
setRetryListener(OnClickListener listener)

```

**属性**

``` xml  
<declare-styleable name="StatusView">
        <!-- 空布局资源id -->
        <attr name="StatusEmptyResId" format="reference"/>
        <!-- 加载中布局资源id -->
        <attr name="StatusLoadingResId" format="reference"/>
        <!-- 错误布局资源id -->
        <attr name="StatusErrorResId" format="reference"/>

        <!-- 空布局展示图片 -->
        <attr name="StatusEmptyImage" format="reference"/>
        <!-- 空布局展示文本 -->
        <attr name="StatusEmptyText" format="string"/>

        <!-- 错误布局图片 -->
        <attr name="StatusErrorImage" format="reference"/>
        <!-- 错误布局文本 -->
        <attr name="StatusErrorText" format="string"/>
        <!-- 错误布局重试按钮文本 -->
        <attr name="StatusRetryText" format="string"/>

        <!-- 加载布局文本 -->
        <attr name="StatusLoadingText" format="string"/>

        <!-- 文本颜色 -->
        <attr name="StatusTextColor" format="color"/>
        <!-- 文本尺寸 -->
        <attr name="StatusTextSize" format="dimension"/>

        <!-- 按钮文本颜色 -->
        <attr name="StatusButtonTextColor" format="color"/>
        <!-- 按钮文本尺寸 -->
        <attr name="StatusButtonTextSize" format="dimension"/>
        <!-- 按钮背景 -->
        <attr name="StatusButtonBackground" format="reference"/>

    </declare-styleable>

``` 


 

## 提问以及反馈
[GitHub 地址](https://jitpack.io/#LZHS/StatusView)  

[QQ 邮箱：1050629507@qq.com](1050629507@qq.com)   
 有问题，请反馈到邮箱，我将第一时间反馈
 
