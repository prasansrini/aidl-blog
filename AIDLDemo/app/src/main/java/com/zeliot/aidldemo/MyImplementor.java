package com.zeliot.aidldemo;

public class MyImplementor
		extends IMyAidlInterface.Stub {

	@Override
	public String getMessage() {
		return "Hello from AIDL Stub!";
	}
}
