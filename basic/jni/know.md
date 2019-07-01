## jni
	javaNativeInterface
	native 本地
	nativeLanuage		
	android的本地语言c/c++
	操作系统用哪种语言开发的,那么对于这个操作系统这种语言就是本地语言

## jni作用
	实现Java和本地语言的相互调用
	android平台 Java和c/c++相互调用
	c可以操作硬件
	c直运行在底层c->机器码
	通过jni把需要效率的逻辑放到c去实现
	使用jni可以访问c的优秀代码
	处于安全考虑

## 交叉编译	
	在一个平台上编译出另一个平台上可以运行的本地代码
	平台
		CPU平台 x86 arm mips 
		操作系统平台 windows linux macos unix
	模拟另外一个平台的特性进行编译
	NDK native develop kit

## nbk目录
	doc文档
	samples 样例工程 供开发人员参考
	sources ndk相关的源代码
	platfoms 根据不同的api-level有多个文件夹 每个文件夹中都有不同cpu架构的文件夹
	include android下做jni开发相关的头文件
	lib google编译好的 jni开发可能用到的函数库
	toolchains 交叉编译工具链
	build/tools .sh linux的批处理命令 通过这些批处理命令调用交叉编译工具链中的编译工具
	nbk-build.cmd 通过这个命令可以开启交叉编译的过程 如果想通过命令行来编译可以在android上运行的本地代码,要把nbk-build放到环境变量中

## jni
	本地方法没找到
		函数名写错了
		忘记写System.loadLibrary,可以通过静态代码块加载.so文件
	找lib的时候(so文件)返回null
		名字写错了,lib前缀去掉,.so后缀去掉,剩下的就是要加载的文件名字
		当前的so文件不能被so文件不被cpu平台支持,需要通过在jni目录下添加Application.mk来指定编译之后so支持的cpu平台

## c++环境
	在c++环境下,jniEnv不再是结构体的一级指针 而是结构体 JNIEnv的别名
	所以env是JNIEnv的一级指针,访问结构体函数 env->
	c++的结构体中可以声明函数,c的结构体中只能声明函数指针 不能有函数的实现
	在JNIEnv这个结构体中 定义了大量的函数 实际上就是调用了结构体JNINativeInterface的同名函数指针,
		并且在调用的时候,把第一个参数JNIEnv*传进去了,所以env调用函数时不需要env作为第一个参数
	c++的函数的在使用的时候,要先声明,可以用生成的.h头文件作为函数的声明,可以把.h头文件放入cpp源代码相同的目录下,
		include "" 把.h头文件导入进来 作为函数的声明,采用jdk的javah com.tuwq.hello.MainActivity就会生成类的.h文件