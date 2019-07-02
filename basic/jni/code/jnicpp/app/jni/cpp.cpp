#include <jni.h> // 使用<> 导入头文件 直接到编译器指定的include目录去找对应.h
#include "com_tuwq_jnicpp_MainActivity.h" //使用 "" 导入头文件 会先到源代码目录找头文件,如果找不到再到系统指定的include找
JNIEXPORT jstring JNICALL Java_com_tuwq_jnicpp_MainActivity_hellocpp
  (JNIEnv * env, jobject obj){
	return env->NewStringUTF("hello from cpp");
}
