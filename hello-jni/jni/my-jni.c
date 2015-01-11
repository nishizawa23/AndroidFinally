#include <string.h>
#include <jni.h>
#include <android/log.h>
#include <assert.h>

#define  LOG_TAG    "myjni"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

/* Set to 1 to enable debug log traces. */
#define DEBUG 1

/* This is a trivial JNI example where we use a native method
 * to return a new VM String. See the corresponding Java source
 * file located at:
 *
 *   apps/samples/hello-jni/project/src/com/example/hellojni/HelloJni.java
 */
jstring Java_com_example_hellojni_HelloJni_exampleFromJNI(JNIEnv* env,
		jobject thiz) {

	return (*env)->NewStringUTF(env, "Example Hello from JNI !");
}

jint JNI_OnLoad(JavaVM* vm, void* reserved) {

	LOGI("JNI_OnLoad");

	JNIEnv* env = NULL;
	jint result = -1;

	if ((*vm)->GetEnv(vm, (void**) &env, JNI_VERSION_1_4) != JNI_OK) {
		LOGE("load library error 1");
		return JNI_ERR;
	}

	assert(env != NULL);

	result = JNI_VERSION_1_4;
	LOGI("load library success: %d", result);
	return result;
}
