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
