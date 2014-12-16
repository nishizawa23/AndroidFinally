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
		//onBind��unOnBind����������������һ��
		Log.i(TAG, "at onBind");
		
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		//����onBind����onStartCommand��ʽ���������onCreate����
		super.onCreate();
		Log.i(TAG, "at onCreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		//������������õ�������,ִ����ɺ󣬱�Ϊ��̨����
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
