#makefile 作用就是向编译系统描述 我要编译的文件在什么位置 要生成的文件叫什么名字 是什么类型
LOCAL_PATH := $(call my-dir)
#清除上次编译的信息
    include $(CLEAR_VARS)
#在这里指定最后生成的文件的名字
    LOCAL_MODULE    := hello
    LOCAL_SRC_FILES := hello.c
#要编译的C的代码的文件名
    include $(BUILD_SHARED_LIBRARY)
    #要生成的是一个动态链接库