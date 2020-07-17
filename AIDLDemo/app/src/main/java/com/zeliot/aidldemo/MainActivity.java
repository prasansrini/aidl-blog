package com.zeliot.aidldemo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity
		extends AppCompatActivity {

	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent intent = new Intent(this, MyService.class);

		//		bindService(intent, new ServiceConnection() {
		//			@Override
		//			public void onServiceConnected(
		//					ComponentName name, IBinder service) {
		//				Log.e(TAG, "Connected");
		//			}
		//
		//			@Override
		//			public void onServiceDisconnected(ComponentName name) {
		//				Log.e(TAG, "Disconnected");
		//			}
		//		}, Context.BIND_AUTO_CREATE);
	}
}