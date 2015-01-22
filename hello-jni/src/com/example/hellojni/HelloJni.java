/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.hellojni;

import android.app.Activity;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class HelloJni extends Activity {
	private static Handler h;
	TextView tv;
	private int numb;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*
		 * Create a TextView and set its content. the text is retrieved by
		 * calling a native function.
		 */
		tv = new TextView(this);
		tv.setText(exampleFromJNI());
		setContentView(tv);
		
		numb = 100;

		h = new Handler() {
			public void handleMessage(Message msg) {
				tv.setText(msg.obj.toString());
			}
		};
		nativeSetup();
		//nativeExecute(10);
		nativeExec();
		
		threadTest();
	}

	private static void setValue(int value) {
		String str = "Value(static) = " + String.valueOf(value);
		Message m = h.obtainMessage(1, 1, 1, str);
		h.sendMessage(m);
	}

	private void setV(int value) {
		String str = "Value = " + String.valueOf(value);
		Message m = h.obtainMessage(1, 1, 1, str);
		h.sendMessage(m);
	}

	/*
	 * A native method that is implemented by the 'hello-jni' native library,
	 * which is packaged with this application.
	 */
	public native String stringFromJNI();

	public native String exampleFromJNI();

	// add by huangxinghua for test c call java
	public native void nativeSetup();

	public native static void nativeExecute(int n);

	public native void nativeExec();
	
	public native void threadTest();

	/*
	 * This is another native method declaration that is *not* implemented by
	 * 'hello-jni'. This is simply to show that you can declare as many native
	 * methods in your Java code as you want, their implementation is searched
	 * in the currently loaded native libraries only the first time you call
	 * them.
	 * 
	 * Trying to call this function will result in a
	 * java.lang.UnsatisfiedLinkError exception !
	 */
	public native String unimplementedStringFromJNI();

	/*
	 * this is used to load the 'hello-jni' library on application startup. The
	 * library has already been unpacked into
	 * /data/data/com.example.hellojni/lib/libhello-jni.so at installation time
	 * by the package manager.
	 */
	static {
		System.loadLibrary("hello-jni");
	}
}
