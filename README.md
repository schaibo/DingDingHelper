## <center/>钉钉自动打卡
>### 前言
>钉钉自动打卡是一款帮助用户完全解放双手的程序。到时间点会自动唤醒程序进行打卡,而且支持远程打卡.


![](./helper2.png)


### 大体思路
作为Android开发人士,相信手上都有不少工程机,利用遗留在公司的工程机,在打卡时间自动执行解锁,启动钉钉,登录打卡等一系列步骤,最后自动退出钉钉并锁屏.


## 使用

#### 基础使用
	需要root,把apk安装到system/app 目录下, 然后打开app, 保存下钉钉密码, 然后选择打卡时间,
	完成, 就是这么简单. AccessibilityService可以不打开, 打卡时间一到会自动打卡.
#### 高级用法
	
   远程一句命令打卡.电脑可以装个TeamViewer,在家随时,随地打卡(爽YY).
   
  `$ am broadcast -a com.ucmap.dingdinghelper.clock `
   
   
   远程开启服务命令
   
 
    $ settings put secure enabled_accessibility_services  com.ucmap.dingdinghelper/com.ucmap.dingdinghelper.services.DingDingHelperAccessibilityService
    $ settings put secure accessibility_enabled 1
   
   
	
   远程关闭服务命令

	$ settings put secure enabled_accessibility_services  com.ucmap.dingdinghelper/com.ucmap.dingdinghelper.services.DingDingHelperAccessibilityService
	$ settings put secure accessibility_enabled 0
	


## 源码解析

#### 钉钉打卡方式
1. 极速打卡,在办公地方自动打卡.
2. 手动进入考勤,定位打卡.


#### 技术
* 四大组件
* AccessibilityService
* adb命令
* 多进程
* 进程保活

#### 思路解析
我相信很多人都利用过AccessibilityService来写自动抢红包,或者流氓安装,模拟点击, 但是AccessibilityService面对WebView却无能为力,不幸的是,钉钉的打卡页面恰恰是WebView.

1. 利用AlarmManager,注册静态广播(打卡时间回调)

	AlarmManager注册定时器广播, 打卡时间一到, 系统回调我们写好的静态广播,然后把DingDingHelperAccessibilityService,钉钉启动.
	
	```
	am.setWindow //把打卡时间转成Calendar,传进入
	```
	
	```
	//
	public class TimingBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("Infoss", "TimingBroadcastReceiver  已经被系统回调");
        //唤醒屏幕,解锁
        mList.add("input keyevent 26");
        /*从下往上滑动解锁*/
        mList.add("input swipe 200 800 200 100");
        //把 DingDingHelperAccessibilityService 唤醒 , 并且把钉钉启动
        命令settings put secure enabled_accessibility_services ...//启动DingDingHelperAccessibilityService
        接着 am start ...启动钉钉
        
        //接下来逻辑交给 DingDingHelperAccessibilityService , 完成打卡并退出.
        ...//省略N多逻辑
       }
    }

	```
	
	
2. DingDingHelperAccessibilityService 监听并处理钉钉窗口等.

	通过下面的代码可以监听到钉钉的窗口的变化,然后模拟点击,把保存好的密码,输入进去, 自动登录,进入打卡界面, 遍历所有节点找到上班打卡节点的AccessibilityNodeInfo, 然后算出打卡的x,y坐标, 通过命令模拟物理点击.
	
	```
	
	switch (eventType) {
            /*窗口变化*/
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                windowContentChanged();
                break;
            //当通知栏发生改变时
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                notificationChanged(event);
                break;
            //当Activity等的状态发生改变时
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                windowChanged(event);
                break;
        }
        
	```	
	
	
	```	
 	  AccessibilityNodeInfo mInfo = mNodeInfos;  //递归找到上班打卡的节点
 	  Rect mRect = new Rect();//构建rect
	  mInfo.getBoundsInScreen(mRect); //给rect赋值
 	  //命令执行物理点击 打卡
	  ShellUtils.execCmd("input tap " + mRect.centerX()+ " " + 	  mRect.centerY(),true)	
	```
	
3. 进程保活
	
	进程一旦被杀死 , 你写的广播也就不会回调 . 保持不死进程是很重要的一步 .
	
	(非root)网上各种保活套路层出不穷,相信除了手机QQ和微信(白名单), 还没有一个应用能很有效果的保活下来.系统要杀你,那轮到你不死.(孩子们别天真了).
	
	(root)有了这个权限就简单多了,跻身一变, 变成系统级别app.
	
	一键变身系统级别app 如下命令
	
	
	```
	mount -o rw,remount yassf2 /system/    //重新挂载	cp /data/app/com.ucmap.dingdinghelper.apk   /system/app/com.ucmap.dingdinghelper.apk  //复制app	到system/app/ 这个目录	chmod 777 /system/app/com.ucmap.dingdinghelper.apk        //修改文件权限	pm install -r \"/system/app/com.ucmap.dingdinghelper.apk\" //安装
		 am start -n \"com.ucmap.dingdinghelper/com.ucmap.dingdinghelper.MainActivity\" -a 	android.intent.action.MAIN -c android.intent.category.LAUNCHER"//启动app	rm /data/app/com.ucmap.dingdinghelper.apk  //删除原有的apk文件	mount -o remount,ro -t yaffs2 /dev/block/mtdblock3 /system    //恢复分区
	```
	
	看下adj值
	
	![](./adj.png)
	
	adj值为-12 , 相当低了, 前台可见进程值0 , 进程神了 .
	
#### 注意事项
* 保证手机完整 Root .
* 保证进程不被杀死 。 
* 保证 am pm input 等命令正常执行。

#### 常见问题
* 成为系统 App 失败(Root -> 系统分区锁->BusyBox指令)
		

### 结束语
    安装上钉钉自动打卡 , 你基本可以开心玩耍 , 每天吃完早餐再去上班 , 不用急急忙忙冲上去打完卡在下来买早餐 ...
    最后我想说: 技术无错 , 看你怎么应用 , 还我快播啊 . (ps:客官们不要迟到啊!)
    
### 欢迎Fork,PR,Star!!  [钉钉自动打卡](https://github.com/Justson/DingDingHelper.git)   

 
	





