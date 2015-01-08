#include <string.h>
#include <jni.h>

/* This is a trivial JNI example where we use a native method
 * to return a new VM String. See the corresponding Java source
 * file located at:
 *
 *   apps/samples/hello-jni/project/src/com/example/hellojni/HelloJni.java
 */
jstring
Java_com_example_hellojni_HelloJni_exampleFromJNI( JNIEnv* env,
                                                  jobject thiz )
{

    return (*env)->NewStringUTF(env, "Example Hello from JNI !");
}
