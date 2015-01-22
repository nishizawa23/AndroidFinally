#include <string.h>
#include <jni.h>
#include <android/log.h>
#include <assert.h>
#include<pthread.h>

#define  LOG_TAG    "myjni"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

/* Set to 1 to enable debug log traces. */
#define DEBUG 1

static const char* kClassName = "com/example/hellojni/HelloJni";

jclass m_class;
jobject m_object, m_rv_object;
;
jmethodID m_mid_static, m_mid;
jfieldID m_fid;
JavaVM *g_jvm = NULL;

jmethodID m_rv_mid;

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
	//clazz 只是一个引用，所以要new一个全局变量来保存
	m_class = (jclass) (*env)->NewGlobalRef(env, clazz);
	m_object = (jobject) (*env)->NewGlobalRef(env, thiz);
	m_mid_static = (*env)->GetStaticMethodID(env, m_class, "setValue", "(I)V");
	m_mid = (*env)->GetMethodID(env, m_class, "setV", "(I)V");
	m_fid = (*env)->GetFieldID(env, clazz, "numb", "I");

	jclass rvClazz = (*env)->FindClass(env, "com/example/hellojni/initFormC");
	jmethodID constr = (*env)->GetMethodID(env, rvClazz, "<init>", "()V");
	jobject ref = (*env)->NewObject(env, rvClazz, constr);

	m_rv_object = (jobject) (*env)->NewGlobalRef(env, ref);
	m_rv_mid = (*env)->GetMethodID(env, rvClazz, "test", "()V");

	(*env)->GetJavaVM(env, &g_jvm);

	return;
}

void CounterNative_nativeExec(JNIEnv *env, jclass clazz) {
	int i, sum = 0;
	int numb = (int) (*env)->GetIntField(env, m_object, m_fid);
	for (i = 0; i <= numb; i++)
		sum += i;
	(*env)->CallVoidMethod(env, m_object, m_mid, sum);

	(*env)->CallVoidMethod(env, m_rv_object, m_rv_mid);

	return;
}
void CounterNative_nativeExecute(JNIEnv *env, jobject thiz, jint n) {
	int i, sum = 0;
	for (i = 0; i <= n; i++)
		sum += i;
	(*env)->CallStaticVoidMethod(env, m_class, m_mid_static, sum);
	return;
}

void *thread_fun(void* arg) {
	JNIEnv *env;

//Attach主线程
	if ((*g_jvm)->AttachCurrentThread(g_jvm, &env, NULL) != JNI_OK) {
		LOGE("%s: AttachCurrentThread() failed", __FUNCTION__);
		return NULL;
	}

	(*env)->CallVoidMethod(env, m_rv_object, m_rv_mid);

//Detach主线程
	if ((*g_jvm)->DetachCurrentThread(g_jvm) != JNI_OK) {
		LOGE("%s: DetachCurrentThread() failed", __FUNCTION__);
	}

	pthread_exit(0);
}

void Native_threadTest(JNIEnv *env, jobject thiz) {
	int i;
	pthread_t* pt;
	pt = (pthread_t*) malloc(5 * sizeof(pthread_t));
	for (i = 0; i < 5; i++) {
		//创建子线程
		pthread_create(&pt[i], NULL, &thread_fun, (void *) i);
	}
	for (i = 0; i < 5; i++) {
		pthread_join(pt[i], NULL);
	}
	LOGI("main thread exit.....");
}

//获取函数签名方法，进入prioject/bin/class 执行 javap -s com.example.hellojni.HelloJni

static JNINativeMethod gMethods[] = { { "exampleFromJNI",
		"()Ljava/lang/String;",
		(void*) Java_com_example_hellojni_HelloJni_exampleFromJNI }, {
		"nativeSetup", "()V", (void*) CounterNative_nativeSetup }, {
		"nativeExecute", "(I)V", (void*) CounterNative_nativeExecute }, {
		"nativeExec", "()V", (void*) CounterNative_nativeExec }, { "threadTest",
		"()V", (void*) Native_threadTest }, };

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
