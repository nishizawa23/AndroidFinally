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
		//应用启动，可在此处初始化数据
		super.onCreate();
		Log.i(TAG, "SimpleApplication at onCreate()");
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		//应用结束，可在此处保存数据
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
		//系统内存过低，可以部署一些内存回收策略
		super.onLowMemory();
		Log.i(TAG, "SimpleApplication at onLowMemory()");
	}

}
