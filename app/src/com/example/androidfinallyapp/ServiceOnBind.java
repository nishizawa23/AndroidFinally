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
		notification.icon = R.drawable.ic_launcher;// ����֪ͨ��ͼ��
		notification.tickerText = "��ʾ��״̬���е�����"; // ��ʾ��״̬���е�����
		long when = System.currentTimeMillis();
		notification.when = when; // ������֪ͨʱ��ʱ��
		// notification.sound =
		// Uri.parse("android.resource://com.sun.alex/raw/dida"); // �Զ�������
		// notification.flags = Notification.FLAG_NO_CLEAR; //
		// ��������ťʱ�ͻ������Ϣ֪ͨ,���ǵ��֪ͨ����֪ͨʱ������ʧ
		notification.flags = Notification.FLAG_ONGOING_EVENT; // ��������ť���������Ϣ֪ͨ,����������ʾ����������
		// notification.flags |= Notification.FLAG_AUTO_CANCEL; //
		// ��������ť����֪ͨ����Զ���ʧ
		// notification.flags |= Notification.FLAG_INSISTENT; //
		// һֱ���У���������һֱ���ţ�֪���û���Ӧ
		// notification.defaults = Notification.DEFAULT_SOUND; // ����ϵͳ�Դ�����
		// notification.defaults = Notification.DEFAULT_VIBRATE;// ����Ĭ����
		// notification.defaults = Notification.DEFAULT_ALL; // ����������
		notification.defaults = Notification.DEFAULT_ALL; // �����е��������ó�Ĭ��

		notification.setLatestEventInfo(this, "֪ͨ", "������", PendingIntent
				.getActivity(this, 0, new Intent(this,
						NotificationActivity.class), 0));// ������תҳ�棬��û��ת

		// ���activity���ã�����������һ��
		// nManager.notify(ID, notification);
		// service��֪ͨ����󶨣��л���ǰ̨ģʽ
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
		// ���activity���ã������������
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
