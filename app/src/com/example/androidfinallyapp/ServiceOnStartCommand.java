package com.example.androidfinallyapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ServiceOnStartCommand extends Service {

	private static final String TAG = "ServiceOnStartCommand";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		//onBind到unOnBind与调用组件生命周期一样
		Log.i(TAG, "at onBind");
		
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		//无论onBind还是onStartCommand方式，都会调用onCreate方法
		super.onCreate();
		Log.i(TAG, "at onCreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		//生命周期与调用的组件相关,执行完成后，变为后台服务
		Log.i(TAG, "at onStartCommand");
		return super.onStartCommand(intent, flags, startId);

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG,"at onDestroy");
	}

}
