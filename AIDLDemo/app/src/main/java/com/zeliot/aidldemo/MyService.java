package com.zeliot.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MyService
		extends Service {

	private MyImplementor myImplementor;

	@Override
	public void onCreate() {
		super.onCreate();
		myImplementor = new MyImplementor();
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return myImplementor;
	}
}
