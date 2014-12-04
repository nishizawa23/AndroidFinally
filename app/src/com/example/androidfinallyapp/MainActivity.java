package com.example.androidfinallyapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	private static final String TAG = "MainActivity";

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
					Log.i(TAG, "onClick button_2");

					break;
				case R.id.button3:
					Log.i(TAG, "onClick button_3");
					break;

				default:
					break;
				}

			}
		};

		button_2.setOnClickListener(handle);
		button_3.setOnClickListener(handle);
	}

	public void ButtonOnClick(View V) {

		Log.i(TAG, "onClick button_4");

	}

	@Override
	public void onClick(android.view.View v) {
		switch (v.getId()) {
		case R.id.main_start_activity:
			Log.i(TAG, "satrt activity");
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
