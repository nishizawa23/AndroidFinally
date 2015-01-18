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

static const char* kClassName = "com/example/hellojni/HelloJni";

jclass m_class;
jobject m_object;
jmethodID m_mid_static, m_mid;
jfieldID m_fid;

/* This is a trivial JNI example where we use a native method
 * to return a new VM String. See the corresponding Java source
 * file located at:
 *
 *   apps/samples/hello-jni/project/src/com/example/hellojni/HelloJni.java
 */
//这个函数可以用javah生成，具体方法是先用eclipse运行一下，生成class
//进入project/bin/class 执行javah -classpath . -jni com.example.hellojni.HelloJni
jstring Java_com_example_hellojni_HelloJni_exampleFromJNI(JNIEnv* env,
		jobject thiz) {

	return (*env)->NewStringUTF(env, "Example Hello from JNI !");
}

void CounterNative_nativeSetup(JNIEnv *env, jobject thiz) {
	jclass clazz = (*env)->GetObjectClass(env, thiz);
	m_class = (jclass)(*env)->NewGlobalRef(env, clazz);
	m_object = (jobject)(*env)->NewGlobalRef(env, thiz);
	m_mid_static = (*env)->GetStaticMethodID(env, m_class, "setValue", "(I)V");
	m_mid = (*env)->GetMethodID(env, m_class, "setV", "(I)V");
	m_fid = (*env)->GetFieldID(env, clazz, "numb", "I");
	return;
}

void CounterNative_nativeExec(JNIEnv *env, jclass clazz) {
	int i, sum = 0;
	int numb = (int) (*env)->GetIntField(env, m_object, m_fid);
	for (i = 0; i <= numb; i++)
		sum += i;
	(*env)->CallVoidMethod(env, m_object, m_mid, sum);
	return;
}
void CounterNative_nativeExecute(JNIEnv *env, jobject thiz, jint n) {
	int i, sum = 0;
	for (i = 0; i <= n; i++)
		sum += i;
	(*env)->CallStaticVoidMethod(env, m_class, m_mid_static, sum);
	return;
}

//获取函数签名方法，进入prioject/bin/class 执行 javap -s com.example.hellojni.HelloJni

static JNINativeMethod gMethods[] = { { "exampleFromJNI",
		"()Ljava/lang/String;",
		(void*) Java_com_example_hellojni_HelloJni_exampleFromJNI }, {
		"nativeSetup", "()V", (void*) CounterNative_nativeSetup }, {
		"nativeExecute", "(I)V", (void*) CounterNative_nativeExecute }, {
		"nativeExec", "()V", (void*) CounterNative_nativeExec }, };

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
