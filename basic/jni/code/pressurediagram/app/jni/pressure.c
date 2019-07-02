#include <jni.h>
#include <stdlib.h>

int getPressure(){
	return rand()%101;
}

int flag = 1;
JNIEXPORT void JNICALL Java_com_tuwq_pressurediagram_MainActivity_startMonitor
  (JNIEnv * env, jobject obj){
	jclass clazz = (*env)->FindClass(env,"com/tuwq/pressurediagram/MainActivity");
	jmethodID methodID = (*env)->GetMethodID(env,clazz,"setPressure","(I)V");
	flag = 1;
	while(flag){
	    // 在linux中 这是睡一秒
        sleep(1);
        (*env)->CallVoidMethod(env,obj,methodID,getPressure());
	}
}

JNIEXPORT void JNICALL Java_com_tuwq_pressurediagram_MainActivity_stopMonitor
  (JNIEnv * env, jobject obj){
	flag = 0;
}
