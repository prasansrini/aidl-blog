package com.zeliot.clientaidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zeliot.aidldemo.IMyAidlInterface;

public class MainActivity
		extends AppCompatActivity {
	private static final String TAG = "MainActivity";
	private IMyAidlInterface iMyAidlInterface;
	private final ServiceConnection mServiceConnection =
			new ServiceConnection() {
				@Override
				public void onServiceConnected(
						ComponentName name, IBinder service) {

					iMyAidlInterface =
							IMyAidlInterface.Stub.asInterface(service);

					try {
						Log.e(TAG, iMyAidlInterface.getMessage());

						Toast.makeText(MainActivity.this, "Received message: " +
						                                  iMyAidlInterface
								                                  .getMessage(),
						               Toast.LENGTH_SHORT).show();
					} catch (RemoteException e) {
						e.printStackTrace();
					}

					Log.d(TAG, "Service Connected.");
				}

				@Override
				public void onServiceDisconnected(ComponentName name) {
					iMyAidlInterface = null;
					Toast.makeText(MainActivity.this, "Service Disconnected.",
					               Toast.LENGTH_SHORT).show();
				}
			};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.click_text).setOnClickListener(v -> {
			try {
				Toast.makeText(this, "" + iMyAidlInterface.getMessage(),
				               Toast.LENGTH_SHORT).show();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});

		Intent intent = new Intent();
		intent.setPackage("com.zeliot.aidldemo");
		intent.setAction("MyService");
		bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
	}

	@Override
	protected void onStop() {
		unbindService(mServiceConnection);
		super.onStop();
	}
}