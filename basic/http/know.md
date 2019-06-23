## 联网注意事项
### 联网必须在子线程操作
### 子线程不能修改UI,修改UI必须在主线程进行,主线程又叫UI线程

## handler
	基于链表实现的消息队列,使得主线程进行操作
	sendMessageDelayed可以定时延迟发送消息
	runOnUiThread是activity的方法,使用run必然运行在主线程