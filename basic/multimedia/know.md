## 计算机表示图形的几种方式
	BMP 质量最高,用于计算机
	PNG 质量高,用于计算机,网络,应用中的图片资源
	jpeg 较高质量,用于计算机,网络,电子邮件
	gif 质量较差,用于计算机,网络,电子邮件

	1byte = 8二进制数
	单色: 一个像素用1位2进制数来表示	40000/8/1024 4.8k
	16色: 一个像素16总颜色2^4,一个像素用半个byte表示 40000/2/1024 19.6k
	256色: 一个像素256总颜色2^8,一个像素用一个byte表示 40000/1/1024 40.1k
	24位位图: 一个像素用24位的2进制数,一个像素占用3byte	  40000*3/1024 117k
	argb8888: 一个像素占用4个byte

##  BitmapFactory.Options
	用于处理图片压缩等

## canvas
	用于修改图片进行绘画等

## mediaplayer
	负责音频的播放和音视频的解码

## sufaceView和videoView
	sufaceView是一个重量级控件,加载需要一些时间
	videoView是对sufaceView和MediaPlayer的封装

## 查询音乐数据
	MediaScaner 当每一次加载SD卡时候,都会扫描卡里的多媒体文件
	MediaStore 获取MediaScaner的数据,保存到MediaProvider
	MediaProvider 提供手机里所有的多媒体文件数据

## MediaPlayer的生命周期
	在prepare之后,音乐资源才被加载到内存里,此时可以调用start,stop
	在stop之后,必须重新Prepare才能恢复歌曲播放
	Prepare可以是同步的也可以是异步的
	stop,reset,release区别
		stop之后想要重新播放歌曲,只要Prepare就可以
		reset之后想要重新播放歌曲,必须要先设置文件路径,再prepare
		release之后,当前MediaPlayer对象不可重用,只能抛弃掉
