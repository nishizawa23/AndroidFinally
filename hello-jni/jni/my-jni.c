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

static const char* kClassName="com/example/hellojni/HelloJni";

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

static JNINativeMethod gMethods[] = { { "exampleFromJNI",
		"()Ljava/lang/String;",
		(void*) Java_com_example_hellojni_HelloJni_exampleFromJNI }, };

static int RegisterNativeMethods(JNIEnv* env, const char* className,
		JNINativeMethod* gMethods, int numMethods) {

	LOGI("RegisterNativeMethods");

	jclass clazz;
	//in C++ use env->FindClass(className);
	clazz = (*env)->FindClass(env, className);

	//in C++ use env->RegisterNatives(clazz, gMethods, numMethods);
	if ((*env)->RegisterNatives(env, clazz, gMethods, numMethods) < 0) {
		return JNI_FALSE;
	}

	return JNI_TRUE;
}

static int registerNatives(JNIEnv *env) {
	if (!RegisterNativeMethods(env, kClassName, gMethods,
			sizeof(gMethods) / sizeof(gMethods[0]))) {
		return JNI_FALSE;
	}
	return JNI_TRUE;
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

	if (registerNatives(env) != JNI_TRUE) {
		LOGE("ERROR: hello jni native registration failed\n");
		goto fail;
	}

	result = JNI_VERSION_1_4;
	LOGI("load library success: %d", result);
	return result;

	fail: return -1;
}
