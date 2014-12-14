package com.example.androidfinallyapp;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

public class SimpleApplication extends Application {

	private static final String TAG = "SimpleApplication";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		//Ӧ�����������ڴ˴���ʼ������
		super.onCreate();
		Log.i(TAG, "SimpleApplication at onCreate()");
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		//Ӧ�ý��������ڴ˴���������
		super.onTerminate();
		Log.i(TAG, "SimpleApplication at onTerminate()");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		Log.i(TAG, "SimpleApplication at onConfiguration()");
	}

	@SuppressLint("NewApi")
	@Override
	public void onTrimMemory(int level) {
		// TODO Auto-generated method stub
		super.onTrimMemory(level);
		Log.i(TAG, "SimpleApplication at onTrimMemory()");
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		//ϵͳ�ڴ���ͣ����Բ���һЩ�ڴ���ղ���
		super.onLowMemory();
		Log.i(TAG, "SimpleApplication at onLowMemory()");
	}

}
