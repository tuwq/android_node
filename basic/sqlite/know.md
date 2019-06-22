SQLite 轻
	亮点: 轻 体积小 几百kb 嵌入式设备
		 绿 不用安装 直接解压就可以使用
		 跨平台 symbain linux windowsmobile android
		 单一文件
	槽点: 多线程操作比较差劲
		  对sql的支持不全面

getWritableDatabase和getReadableDatabase区别
	创建打开一个数据库,获得的都是可读可写的数据库
	当磁盘满的时候getReadableDatabase返回一个只读的数据库,而getWritableDatabase会出错
