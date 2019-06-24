# activity
	activity代表一个用户的界面,每一个android的界面对应一个activity
	activity可以创建一个窗口,在这个窗口上加载用户交互的界面
	activity创建的时间就会调用onCreate

# 上下文
	getApplicationContext()获取的是应用的上下文,该上下文生命周期是整个应用的生命周期,使用安全
	使用activity作为上下文,可能会产生内存泄漏