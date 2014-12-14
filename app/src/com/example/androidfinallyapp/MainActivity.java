package com.example.androidfinallyapp;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	private static final String TAG = "MainActivity";
	private static final String INTENT_START_ACTIVITY = "com.example.androidfinallyapp.StartAcitvity";
	private static final String INTENT_START_ACTIVITY_TWO = "com.example.androidfinallyapp.StartAcitvityTwo";
	private static final String INTENT_START_TEST_APP = "com.example.androidfinallyapp.TestApp";
	private static final Uri INTENT_DATA = Uri
			.parse("file:///sdcard/sample.data");

	Button startActivityButton;
	Button button_1;
	Button button_2;
	Button button_3;
	Button button_4;

	View.OnClickListener handle;

	// OnClickListener startActivityListener;
	// http://www.cnblogs.com/ruiati/archive/2013/09/11/3314229.html
	// http://www.cnblogs.com/lingyun1120/archive/2011/09/29/2195449.html

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		startActivityButton = (Button) findViewById(R.id.main_start_activity);
		startActivityButton.setOnClickListener(this);

		button_1 = (Button) findViewById(R.id.button1);
		button_1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction(INTENT_START_ACTIVITY_TWO);
				// http://www.cnblogs.com/newcj/archive/2011/08/10/2134305.html
				intent.setType("text/plain");// MIME
				startActivity(intent);
				Log.i(TAG, "onClick button_1");

			}
		});

		button_2 = (Button) findViewById(R.id.button2);
		button_3 = (Button) findViewById(R.id.button3);

		handle = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.button2:
					// http://blog.csdn.net/sunhengzhi_212/article/details/8274834
					Log.i(TAG, "onClick button_2");
					Intent intent2 = new Intent();
					// same as intent.setClass(MainActivity.this,
					// StartActivity.class);
					ComponentName com = new ComponentName(MainActivity.this,
							StartActivity.class);
					intent2.setComponent(com);
					intent2.putExtra("sengto", false);
					startActivity(intent2);

					break;
				case R.id.button3:
					Log.i(TAG, "onClick button_3");
					Intent intent3 = new Intent();
					intent3.setAction(INTENT_START_TEST_APP);
					startActivity(intent3);
					break;

				default:
					break;
				}

			}
		};

		button_2.setOnClickListener(handle);
		button_3.setOnClickListener(handle);

		int pid = android.os.Process.myPid();
		Log.i(TAG, "MainActivity pid is :" + pid);
	}

	public void ButtonOnClick(View V) {

		Log.i(TAG, "onClick button_4");

	}

	@Override
	public void onClick(android.view.View v) {
		switch (v.getId()) {
		case R.id.main_start_activity:
			Log.i(TAG, "satrt activity");

			Intent intent = new Intent();
			intent.setAction(INTENT_START_ACTIVITY);
			intent.setDataAndType(INTENT_DATA, "image/jpeg");
			intent.putExtra("sms_body",
					"3g android http://www.android-study.com");
			intent.putExtra("sengto", true);
			startActivity(intent);
			break;

		default:
			break;
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
