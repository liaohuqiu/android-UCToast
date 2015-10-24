[![Android Gems](http://www.android-gems.com/badge/liaohuqiu/android-UCToast.svg?branch=master)](http://www.android-gems.com/lib/liaohuqiu/android-UCToast)

UC 浏览器复制，无需权限提示悬浮窗实现

ABOUT ME / 关注我:  [Github](https://github.com/liaohuqiu) | [twitter](https://twitter.com/liaohuqiu) | [微博](http://weibo.com/liaohuqiu)

更多的关于本项目，以及悬浮窗权限细节以及总结，请看：

*  [廖祜秋liaohuqiu_秋百万][] 的 [Android 悬浮窗的小结](http://liaohuqiu.net/cn/posts/android-windows-manager/)

*  [睡不着起不来的万先生][] 的 [Android悬浮窗使用TYPE_TOAST的小结](http://www.jianshu.com/p/634cd056b90c)

---

[睡不着起不来的万先生](http://weibo.com/2951317192) 的 [Android无需权限显示悬浮窗, 兼谈逆向分析app](http://www.jianshu.com/p/167fd5f47d5c) 文中提到，`type` 为 `WindowManager.LayoutParams.TYPE_TOAST` 的 `WindowManager.LayoutParam` 无需权限，即可让 View 显示。

本项目模拟实现该功能，即：开机自动启动的 Service 监听剪切板。复制之后，在屏幕顶部显示一个悬浮窗，显示剪贴板内容。点击悬浮窗，跳转到 Activity 页面显示。

兼容到 API level 9。

包含以下几个小功能点：

1.  监控剪切板
2.  WindowManager 的使用
3.  Service 的使用
4.  悬浮窗处理: 

    1.  黑色半透明背景
    2.  触摸背景关闭
    3.  点击内容跳转
    4.  处理返回键关闭

5.  开机自动启动 Service
6.  WakeLock 启动 Service


<div><img src='https://raw.githubusercontent.com/liaohuqiu/android-UCToast/master/art/uc-toast.gif' width="300px" style='border: #f1f1f1 solid 1px'/></div>

---

[廖祜秋liaohuqiu_秋百万]:      http://weibo.com/liaohuqiu
[睡不着起不来的万先生]:        http://weibo.com/2951317192
