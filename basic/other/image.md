大图片处理
	大图片处理核心思想: 把图片缩小(采样率),图片颜色存储配置(Config.ARGB_4444)
	大图片处理核心类: BitmapFactory.Option
		inJustDecodeBounds: true/false, true:不用把图片真正加载到内存即可得到宽度和高度
		inSampleSize: 采样率,最好使用2的指数,如2(1/4)
		Bitmap.Config inPreferredConfig: 位图存储配置,
			用于指定什么样的方式存储位图像素信息,默认使用最好的配置,Bigmap.Config.ARGB_8888 
	步骤:
		1. 得到实际的宽度和高度
		2. 根据最大宽和最大高得到理想的宽度和高度
		3. 根据理想的宽度和高度和实际的宽度和高度计算出最好的采样值(inSampleSize)
