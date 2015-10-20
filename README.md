UC 浏览器复制，无需权限提示悬浮窗实现

[Android无需权限显示悬浮窗, 兼谈逆向分析app](http://www.jianshu.com/p/167fd5f47d5c) 文中提到，`type` 为 `WindowManager.LayoutParams.TYPE_TOAST` 的 `WindowManager.LayoutParam` 无需权限，即可让 View 显示。

本项目模拟实现该功能，即：开机自动启动的 Service 监听剪切板。复制之后，在屏幕顶部显示一个悬浮窗，显示剪贴板内容。点击悬浮窗，跳转到 Activity 页面显示。

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