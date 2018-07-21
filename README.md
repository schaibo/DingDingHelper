## <center/>钉钉自动打卡
>### 前言
>钉钉自动打卡是一款帮助用户完全解放双手的程序。到时间点会自动唤醒程序进行打卡，而且支持远程打卡。


![](./helper2.png)


### 大体思路
作为Android开发人士，相信手上都有不少工程机，利用遗留在公司的工程机，在打卡时间自动执行解锁，启动钉钉，登录打卡等一系列步骤，最后自动退出钉钉并锁屏。


## 使用

#### 基础使用
	需要root，把apk安装到system/app 目录下， 然后打开app， 保存下钉钉密码， 然后选择打卡时间，
	完成， 就是这么简单. AccessibilityService可以不打开， 打卡时间一到会自动打卡.
#### 高级用法
	
   远程一句命令打卡.电脑可以装个TeamViewer在家随时，随地打卡。
   
  `$ am broadcast -a com.ucmap.dingdinghelper.clock `
   
   
   远程开启服务命令
   
 
    $ settings put secure enabled_accessibility_services  com.ucmap.dingdinghelper/com.ucmap.dingdinghelper.services.DingDingHelperAccessibilityService
    $ settings put secure accessibility_enabled 1
   
   
	
   远程关闭服务命令

	$ settings put secure enabled_accessibility_services  com.ucmap.dingdinghelper/com.ucmap.dingdinghelper.services.DingDingHelperAccessibilityService
	$ settings put secure accessibility_enabled 0
	


 
	





