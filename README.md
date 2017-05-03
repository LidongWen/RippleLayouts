# Material Design水波纹触控效果
MaterialDesign 水波纹触摸效果，**兼容版本 12**  ！    
MaterialDesign water ripple touch effect, ** compatible version 12 **!    
  
Java层代码调用**对代码无侵入性！对代码无侵入性！对代码无侵入性！**  
Java layer code calls, does not affect the control event, ** no intrusion on the code! No intrusion on the code! No intrusion on the code! **  

完美处理触摸事件，完美解耦依赖View  
Perfect handling touch events, perfect decoupling dependency View  

![two.gif](http://upload-images.jianshu.io/upload_images/1599843-0498c31dc10b0250.gif?imageMogr2/auto-orient/strip)

![](http://upload-images.jianshu.io/upload_images/1599843-98223a576a2aa0c9.gif?imageMogr2/auto-orient/strip)




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
    compile 'com.github.LidongWen:RippleLayouts:0.0.3'

// 根目录下引用  root dir reference
    repositories {
        jcenter()
        maven { url "https://www.jitpack.io" }
    }

```

## 内存泄漏测试
![内存情况：无内存泄漏](http://upload-images.jianshu.io/upload_images/1599843-a3f488fbe5fa8e5d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

> ## 0.0.3
> 修复滑动view，水波纹固定bug；
> 修复 showTime 设置无效bug；
> ## 0.0.2
> good

代码传送门：[戳我!!!](https://github.com/LidongWen/RippleLayouts)



-----

希望我的文章不会误导在观看的你，如果有异议的地方欢迎讨论和指正。
如果能给观看的你带来收获，那就是最好不过了。

##### 人生得意须尽欢, 桃花坞里桃花庵
##### 点个关注呗，[对，不信你点试试？](http://www.jianshu.com/users/99f514ea81b3/timeline)
