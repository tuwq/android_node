#include <stdlib.h>
#include <stdio.h>
#include<jni.h>

// 本地函数命名规则 Java_包名_native函数所在类的类名_native方法名
// 第二个参数 jobject 就是调用当前native方法的Java对象
// 第一个参数 JNIEnv* JNIEnv是结构体 JNI
// env又是JNIEnv的一级指针 那么env就是JNINativeInterface的二级指针
// 结构体JNINativeInterface定义了大量的函数指针 这些函数指针在JNI开发中十分常用
// (**env).func (*env)->
//jstring Java_com_example_hellojni_HelloJni_stringFromJNI(JNIEnv* env, jobject thiz)
jstring Java_com_tuwq_jni_1hello_MainActivity_helloInc(JNIEnv* env, jobject thiz) {
    char* str = "hello from c!!";
    return (*env)->NewStringUTF(env,str);
}


