#include <jni.h>
#include <android/log.h>
#define LOG_TAG "System.out"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

JNIEXPORT void JNICALL Java_com_tuwq_ccalljava_JNI_callbackvoid
  (JNIEnv * env, jobject thiz){
    // 找到字节码
	jclass clazz = (*env)->FindClass(env,"com/tuwq/ccalljava/JNI");
	// 找到方法
	// 最后一个参数是方法签名(参数和返回值)
	// 通过jdk的命令 javap -s com.tuwq.ccalljava.JNI 可以查看到类的所有方法签名
	jmethodID methodID = (*env)->GetMethodID(env,clazz,"helloFromJava","()V");
    // 创建对象(可选) 如果native方法和要和回调的方法在同一个Java类中 就不用创建对象,直接使用传进来的对象jobject
	// 第一个参数 JNIEnv
	// 第二个参数 要调用方法的对象
	// 第三个参数 要调用的方法的methodid变量
	// ...可变的参数 调用方法时如果有参数 就是后面可变参数 要传入的内容
	(*env)->CallVoidMethod(env,thiz,methodID);
}

JNIEXPORT void JNICALL Java_com_tuwq_ccalljava_JNI_callbackInt
  (JNIEnv * env, jobject thiz){
	jclass clazz = (*env)->FindClass(env,"com/tuwq/ccalljava/JNI");
	jmethodID methodID = (*env)->GetMethodID(env,clazz,"add","(II)I");
	int result = (*env)->CallIntMethod(env,thiz,methodID,3,4);
	LOGD("result=%d",result);
}

JNIEXPORT void JNICALL Java_com_tuwq_ccalljava_JNI_callbackString
  (JNIEnv * env, jobject thiz){
	jclass clazz = (*env)->FindClass(env,"com/tuwq/ccalljava/JNI");
	jmethodID methodID = (*env)->GetMethodID(env,clazz,"printString","(Ljava/lang/String;)V");
	jstring jstr = (*env)->NewStringUTF(env,"hello");
	(*env)->CallVoidMethod(env,thiz,methodID,jstr);
}

JNIEXPORT void JNICALL Java_com_tuwq_ccalljava_MainActivity_callbackShowToast
  (JNIEnv * env, jobject thiz){
	jclass clazz = (*env)->FindClass(env,"com/tuwq/ccalljava/MainActivity");
	jmethodID methodID = (*env)->GetMethodID(env,clazz,"showToast","(Ljava/lang/String;)V");
	// 创建activity对象是android不允许的,会出现错误
	// jobject obj = (*env)->AllocObject(env,clazz);
	jstring jstr = (*env)->NewStringUTF(env,"call back hello");
	(*env)->CallVoidMethod(env,thiz,methodID,jstr);
}
