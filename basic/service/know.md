## 进程的概念与进程优先级
	当应用运行之后,系统会创建一个linux进程,大部分情况下一个android应用对应一个linux进程
	这个进程在一开始的时候只有一个线程

## 进程优先级
	ForeGroundProcess 前台进程
		当前进程中,有activity处于可见可操作的状态(activity执行onResume之后,并且留在了这个状态,正在被用户操作)
		service执行生命周期方法以及广播接收(onRecevier)
		前台进程几乎不会被杀死
	VisibleProcess 可视进程
		有activity处于onPause状态,可见不可操作(透明应用盖在上面,或者是一个对话框activity盖在上面)
		只有当前台进程内存不够的时候才会杀死可视进程
	ServiceProcess 服务进程
		用startService开启了服务,并且运行在后台,而且没有其他组件处于前两档状态,很少被杀死
	BackgroundProcess 后台进程
		activity处于onStop状态,但没有被销毁
		通常会有大量的应用处于后台进程的状态,哪个应用的进程先被系统回收取决于LRU,最早使用的应用先被杀死
	EmptyProcess 空进程
		没有任何的组件在运行,保存这个空进程的目的是为了缓存当前的进程,加快下次启动的时间,最容易被杀死

## 开启服务
	startService(intent): 通过这种方式开启的服务,执行的声明周期方法,与activity无关
		第一次调用startService时候,执行onCreate()->onStartCommand()
		再次调用startService时候,只执行onStartCommand()
		停止用startService开启的服务,使用stopService(intent),执行onDestroy,执行后service销毁
		再次调用stopService没有反应

	如果在activity中通过startService方法开启一个服务,当activity退出的时候,service不会销毁,依然在后台运行
		只有手动调用stopService或者在应用管理器中关闭service,服务才会销毁
	bindService开启的服务跟activity之间的关系同死
	bindService开启服务
		第一次调用bindService时候,执行onCreate()->onBind()->onServiceConnected()
		再次调用bindService时候,只执行onServiceConnected()
		activity退出的时候必须通过unbindService关闭服务
		再次调用unbindService将报错崩溃


