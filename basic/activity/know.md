# activity
	activity代表一个用户的界面,每一个android的界面对应一个activity
	activity可以创建一个窗口,在这个窗口上加载用户交互的界面
	activity创建的时间就会调用onCreate

# 上下文
	getApplicationContext()获取的是应用的上下文,该上下文生命周期是整个应用的生命周期,使用安全
	使用activity作为上下文,可能会产生内存泄漏

# 创建activity
	类继承activity
	重写onCreate提供初始化
	在main.xml中声明一个activity信息

## 隐式意图和显式意图
	通过匹配main.xml中的intent-filter的action,data等内容打开对应的activity称为隐式意图
	通过类名直接指定打开对应activity称为显式意图

## 跳转api
	startActivity: 跳转activity
	startActivityForResult: 跳转activity并可通知获取关闭时返回数据
	setResult: 设置返回数据
	onActivityResult(){}: 接收返回通知

## activity的四种状态
	前台状态: 可见且可以跟用户进行交互
	暂停状态: 可见但不能被操作
	停止状态: 不可见也不能被操作
	销毁状态: actitivy被系统杀死或者调用finish方法主动退出

## 生命周期
	onCreate: 当activity创建的时候会走这个方法
	onStart:  走完这个activity就可以被用户看到
	onResume: 走完这个activity就处于一个前台activity的状态(可见可以被操作),加载数据,恢复播放状态
	onPause:  走玩这个activity就处于暂停状态(可见不可以被操作)
	onStop:   走完这个activity就处于停止状态(不可见不可以被操作),停止所有关于刷新界面的操作
	onDestory: 走完这个activity就被销毁,释放资源的操作
	从停止状态(onStop执行后)回到前台
	onRestart()
	onStart()
	onResume()
	
