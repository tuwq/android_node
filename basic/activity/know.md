## activity
	activity代表一个用户的界面,每一个android的界面对应一个activity
	activity可以创建一个窗口,在这个窗口上加载用户交互的界面
	activity创建的时间就会调用onCreate
	helloworld

## 两种上下文
	activity.this Context子类 表示当前的activity,只能在当前的activity中使用
	getApplicationContext() 返回Context,表示应用程序的总的上下文,任何activity中都可以用
		在activity中都可以使用,在activity中getApplicationContext()可以取代activity.this
		特殊情况: dialog中使用,要用activity.this,否则无法找到挂载的activity

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
	onDestory: 走完这个activity就被销毁(系统释放或finish时),释放资源的操作
	从停止状态(onStop执行后)回到前台
	onRestart()
	onStart()
	onResume()

## activity任务栈
	standard: 标准模式,默认就是这种模式,只要调用startActivity(startActivityforResult)就会创建activity对应示例
	singleTop: 在任务栈的栈顶只有一个实例,如果栈顶存在一个实例,再创建这个activity的对象,不会成功(扫码最终操作)
	singleTask: 在任务栈中只有一个实例,如果栈中已经存在了一个singleTask的activity,那么再次开启这个activity,不会创建新的对象,
		而是把这个activity上面的所有activity都关闭,把这个activity露出来(主界面)
	singleInstance: 在当前设备只有一个实例,并且这个实例会创建一个单独的任务栈,这个栈中只有这一个实例(浏览器)
	
