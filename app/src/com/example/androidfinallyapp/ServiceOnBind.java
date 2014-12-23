package com.example.androidfinallyapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ServiceOnBind extends Service {

	private static final String TAG = "ServiceOnBind";
	public SimpleBinder sBinder;
	private NotificationManager nManager;
	private Notification notification;
	private static final int ID = 1;

	public class SimpleBinder extends Binder {

		public ServiceOnBind getService() {
			return ServiceOnBind.this;
		}

		public int add(int a, int b) {
			Log.i(TAG, "simpleBinder add func");
			return a + b;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onBind");
		showNotification();

		return sBinder;
	}

	@SuppressWarnings("deprecation")
	private void showNotification() {
		// TODO Auto-generated method stub
		notification.icon = R.drawable.ic_launcher;// 设置通知的图标
		notification.tickerText = "显示在状态栏中的文字"; // 显示在状态栏中的文字
		long when = System.currentTimeMillis();
		notification.when = when; // 设置来通知时的时间
		// notification.sound =
		// Uri.parse("android.resource://com.sun.alex/raw/dida"); // 自定义声音
		// notification.flags = Notification.FLAG_NO_CLEAR; //
		// 点击清除按钮时就会清除消息通知,但是点击通知栏的通知时不会消失
		notification.flags = Notification.FLAG_ONGOING_EVENT; // 点击清除按钮不会清除消息通知,可以用来表示在正在运行
		// notification.flags |= Notification.FLAG_AUTO_CANCEL; //
		// 点击清除按钮或点击通知后会自动消失
		// notification.flags |= Notification.FLAG_INSISTENT; //
		// 一直进行，比如音乐一直播放，知道用户响应
		// notification.defaults = Notification.DEFAULT_SOUND; // 调用系统自带声音
		// notification.defaults = Notification.DEFAULT_VIBRATE;// 设置默认震动
		// notification.defaults = Notification.DEFAULT_ALL; // 设置铃声震动
		notification.defaults = Notification.DEFAULT_ALL; // 把所有的属性设置成默认

		notification.setLatestEventInfo(this, "通知", "开会啦", PendingIntent
				.getActivity(this, 0, new Intent(this,
						NotificationActivity.class), 0));// 即将跳转页面，还没跳转

		// 如果activity调用，则用下面这一句
		// nManager.notify(ID, notification);
		// service与通知对象绑定，切换至前台模式
		startForeground(ID, notification);

	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

		super.onCreate();
		sBinder = new SimpleBinder();

		nManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notification = new Notification();
		Log.i(TAG, "onCreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onDestroy");
		super.onDestroy();
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onLowMemory");
		super.onLowMemory();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onUnbind");
		// 如果activity中用，则用下面这句
		// nManager.cancel(ID);
		stopForeground(true);
		return super.onUnbind(intent);
	}

	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onRebind");
		super.onRebind(intent);
	}

}
