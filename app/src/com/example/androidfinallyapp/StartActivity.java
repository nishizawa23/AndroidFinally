package com.example.androidfinallyapp;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class StartActivity extends ActionBarActivity {

	private static final String TAG = "StartActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		Intent intent = getIntent();
		// ����������������Intent����û�����category�ϵͳ���Զ����CATEGORY_DEFAULT��
		// ���Ҫ��Intent Filter�����б������Intent.GATEFORY_DEFAULT��𣬲���ƥ�����Intent
		// ����
		String action = intent.getAction();
		Log.i(TAG, "getAction is : " + action);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
