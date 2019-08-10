## 联网注意事项
### 联网必须在子线程操作
### 子线程不能修改UI,修改UI必须在主线程进行,主线程又叫UI线程

## handler
	基于链表实现的消息队列,使得主线程进行操作
	sendMessageDelayed可以定时延迟发送消息
	runOnUiThread是activity的方法,使用run必然运行在主线程

## okhttp
	okhttp用于替代HttpUrlConnection和ApacheHttpClient
	okhttp支持spdygoogle开发的基于tcp的应用层,用以最小化网络延迟,提升网络速度,优化用户的网络使用体验,共享同一个socket来处理同一个服务器的所有	请求
	如果spdy不可用,则通过连接池来减少请求延迟
	无缝的支持gzip来减少数据流量
	缓存响应数据来减少重复的网络请求

## handler消息机制
	handlerMessageLooper MessageQueue
	looper和messageQueue的创建
	主线程不需要程序员创建looper和MessageQueue 由系统调用
	Looper.prepareMailLooper
	调用的就是Looper.prepare()方法
	通过一个线程级单例threadLocal把looper保存起来
	在调用Looper.prepare()的时候先通过threadLocal去获取当前线程的looper,如果能获取到直接抛异常
	这样就保证了一个线程对应一个Looper
	Looper在创建的过程中,调用了MessageQueue的构造创建了一个MessageQueue,通过一个final类型成员变量保存起来,
		这样一个Looper对应一个Looper对应一个MessageQueue
	MessageQueue在创建时候,创建一个NativeMessageQueue,NativeMessageQueue在创建的时候又创建了一个C++的looper
	MessageQueue通过一个int类型的成员变量,mptr保存了NativeMessageQueue的指针 可以随时通过jni的调用找到NativeMessageQueue进程从而找到looper
	消息循环过程中,阻塞机制是通过NativeMesageQueue和C++的Looper,在底层实现的,用到了linux管道和epoll机制

## asyncTask原理
	实际上就是对handler的封装
	方法进行了封装 onPreExecute,onPostExecute,doInBackground,使用线程池,可以对线程进行复用
	需要注意3.0之后,直接使用asyncTask.execute方法,使用的是串行的线程池,所有任务是一个个执行的,按照加入到队列中先后顺序
	如果想让asyncTask多个任务并行执行,需要调用new MyAsyncTask().executeOnExecuteor(AsyncTask.THREAD_POOL_EXECUTOR,"")
