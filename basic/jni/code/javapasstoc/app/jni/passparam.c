#include <jni.h>
#include<stdlib.h>
#include <android/log.h>
// 取别名
#define LOG_TAG "System.out"
// 用于打印,具体查看android/log.h
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

/*
    把一个jstr转换成c语言的char*类型
*/
char* _JString2CStr(JNIEnv* env, jstring jstr) {
	 char* rtn = NULL;
	 // 找到Java的String的字节码
	 jclass clsstring = (*env)->FindClass(env, "java/lang/String");
	 // 把字符串GB2312从char*转换成jstring
	 jstring strencode = (*env)->NewStringUTF(env,"GB2312");
	 // 找到String的getBytes方法
	 jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes", "(Ljava/lang/String;)[B");
	 // 调用getByte方法得到byte[]
	 jbyteArray barr = (jbyteArray)(*env)->CallObjectMethod(env, jstr, mid, strencode); // String .getByte("GB2312");
	 // 获取数组长度
	 jsize alen = (*env)->GetArrayLength(env, barr);
	 // 获取数组的首地址
	 jbyte* ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
	 // 长度大于零,是否有内容
	 if(alen > 0) {
	    // 申请一块字符串大小的内存,最后多出一位结束符
		rtn = (char*)malloc(alen+1); //"\0"
		memcpy(rtn, ba, alen); // 内存拷贝
		rtn[alen]=0; // 结束符
	 }
	 (*env)->ReleaseByteArrayElements(env, barr, ba,0); // 释放数组
	 return rtn;
}

// 接收int类型
JNIEXPORT jint JNICALL Java_com_tuwq_javapasstoc_JNI_add
  (JNIEnv * env, jobject thiz, jint x, jint y){
    return x+y;
}

// 接收string类型
JNIEXPORT jstring JNICALL Java_com_tuwq_javapasstoc_JNI_sayHelloInC
  (JNIEnv * env, jobject thiz, jstring jstr){
	// 把Java的字符串转换成C的字符串
	//char* cstr = _JString2CStr(env,jstr);
	char* cstr = (*env)->GetStringUTFChars(env,jstr,NULL);
	// 取字符串长度
	int length = strlen(cstr);
	int i;
	// 给每一个元素+1
	for(i=0;i<length;i++){
		*(cstr+i)+=1;
	}
	// 将c字符串转换为jstring类型
	return (*env)->NewStringUTF(env,cstr);
}

JNIEXPORT jintArray JNICALL Java_com_tuwq_javapasstoc_JNI_arrElementsIncrease
  (JNIEnv * env, jobject thiz, jintArray jarr){
	// 获取数组的长度
	int length = (*env)->GetArrayLength(env,jarr);
	LOGD("length=%d",length);
	// jboolean java的boolean类型在c中的表示
	// 这个参数 在有些虚拟机中 用户作为 执行完GetIntArrayElements之后是否创建了数组的副本标志
	// 如果创建了副本 就会是true  1
	// 如果没创建副本 就会是false 0 android中 这个参数不必使用 直接传NULL 没有意义的地址
	jboolean isCopy = NULL;
	// 获取到数组首地址
	int* p = (*env)->GetIntArrayElements(env,jarr,NULL);
	int i ;
	for(i = 0;i<length;i++){
		*(p+i)+=10;
	}
    // 由于获取到了数组的首地址,所以通过指针操作了每一个元素之后,jarr里的每一个元素已经被修改
    // 所以没有必要创建新的数组,直接把jarr作为返回值
	return jarr;
}
