# RippleLayout
MaterialDesign 水波纹触摸效果，**兼容版本 12**  ！    
MaterialDesign water ripple touch effect, ** compatible version 12 **!  
Java层代码调用，不影响控件事件，**对代码无侵入性！对代码无侵入性！对代码无侵入性！**  
Java layer code calls, does not affect the control event, ** no intrusion on the code! No intrusion on the code! No intrusion on the code! **

<img width="320" height="500" src="https://github.com/LidongWen/RippleLayouts/blob/master/art/one.gif"></img>  <img width="320" height="500" src="https://github.com/LidongWen/RippleLayouts/blob/master/art/two.gif"></img> 


## 使用 Use
### 单独控件使用  Use separate controls
```
    <wenld.github.ripplelayout.RippleLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:ripple_radius="100dp"
        app:ripple_maxCount="10"
        app:ripple_background="#00FFFFFF"
        app:ripple_interval="50"
        app:ripple_show_time="600"
        app:ripple_color="@color/colorAccent"
        />
```
### 辅助其他控件使用  Assist other controls to use
```
        new RippleHelper(this, iv_001);
//                .setRippleBackgroundResource(R.color.half_colorAccent)
//                .setRippleColorResource(R.color.colorAccent);
```

### 支持属性  Support attribute
 有相对应 java 方法   There is a corresponding java method
```
        <attr name="ripple_show_time" format="integer" />  水波纹展现时间  Water ripples show time
        <attr name="ripple_radius" format="dimension" />   水波纹最大半径  Maximum ripple of water ripple
        <attr name="ripple_color" format="color|reference" /> 水波纹颜色  Water ripple color
        <attr name="ripple_background" format="color|reference" /> 水波纹背景色  Water ripple background color
        <attr name="ripple_maxCount" format="integer" />     水波纹最大数量    Maximum number of water ripples
        <attr name="ripple_interval" format="integer" />     水波纹生成最小时间间隔  Water ripple generates minimum time interval
```


## 引用 reference
```groovy
// 项目引用   Project reference
    compile 'com.github.LidongWen:RippleLayouts:0.0.2'

// 根目录下引用  root dir reference
    repositories {
        jcenter()
        maven { url "https://www.jitpack.io" }
    }

```
