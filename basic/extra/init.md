## android启动的流程
	init启动
		BootLoader作用 -> 加载驱动 启动硬件 加载linux内核
		系统的第一个进程 init进程 对应启动代码 init.rc
		runtime.start('com.android.internal.os.ZygoteInit', 'start-system-server')
		以服务形式启动zygote,zygote进程是android中十分重要的进程,孵化器进程
	zygote启动
		1. zygote进程创建了一个jvm
		2. zygote创建了一个socket服务端 等待ams给它发消息 分叉新的进程
		3. 预加载了资源包括 字节码 图片颜色
		4. 跑起死循环,等待客户端连接
		5. 创建SystemServer进程	
