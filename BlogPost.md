![ANDROID-AIDL](https://res.cloudinary.com/practicaldev/image/fetch/s--nAEiJC_k--/c_imagga_scale,f_auto,fl_progressive,h_420,q_auto,w_1000/https://res.cloudinary.com/practicaldev/image/fetch/s--BuZSE9qE--/c_imagga_scale%2Cf_auto%2Cfl_progressive%2Ch_420%2Cq_auto%2Cw_1000/https://dev-to-uploads.s3.amazonaws.com/i/7nb0or6d4it4qku8cuir.png)

### **Introduction**
Hello there! I am going to talk about Android's parliamentary conversations. What do I mean by that? I mean, the IPC(Inter Process Communication) that is happening inside Android system. We are going to use AIDL for the so called "conversation". I will explain better and deeper in this article.

### **We all know it!**

Why is this a brand new post? Because, it's not about the IPC of components inside one Android application. It's about the communication of two different applications. Most people agree to disagree that it's pretty easy to understand AIDLs. But, there is saying(of mine), `"If you understand interfaces in OOP, you shall understand AIDL!"`. Well, Let's see what that saying really means, in detail.

### **The AIDL unbolted.**

Android Interface Definition Language(AIDL), is what you every time you Google AIDL. This doesn't explain what it really is. Let me explain! AIDL is an Android implementation to achieve Inter Process Communication(IPC) in between Android components. Diving deep, there is a program called `aidl`, which compiles the AIDL source code and generates client(Proxy) and server(Stub) Java interfaces.

&nbsp;

![Alt Text](https://dev-to-uploads.s3.amazonaws.com/i/prgype5k7uh08zi40kly.png)

&nbsp;

### **Initial building blocks.**

Let us start with the basics. To create an AIDL, we need to open an Android Project and click `File -> New -> AIDL -> AIDL File`. Write your first AIDL file.

```
interface IMyAidlInterface {
    String getMessage();
}
```

Then, do not forget to `Build -> Rebuild`. The `IMyAidlInterface` class will be generated. Create a class which extends the `IMyAidlInterface.Stub`. Please remember to override `getMessage()` method in this class.

```
public class MyImplementor extends IMyAidlInterface.Stub {
	@Override
	public String getMessage() {
		return "Hello from AIDL Stub!";
	}
}
```

Once we have extended the Stub class and implemented(overridden) the method, we must create a Service which is going to expose the APIs to the applications which need bind the AIDL functionalities.

```
public class MyService extends Service {
	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return new MyImplementor();
	}
}
```

Also, add the `<service>` tag in `AndroidManifest.xml` inside `<application>` tag.
```
<service
    android:name=".MyService"
    android:enabled="true"
    android:exported="true"
    android:process=":remote">
    <intent-filter>
        <action android:name="MyService" />
    </intent-filter>
</service>
```
&nbsp;

Now that we have implemented the Service part, we should go ahead and create another Android project for the client part. Please note that the AIDLs are very **case-sensitive**. Unfortunately, Android Studio is not mature enough to detect the syntax errors in an AIDL file. Any changes in an AIDL file would need a rebuild of the project.
&nbsp;

## **The Real River to cross!**

We are done with the server(Service) part. Now, we should create another application which will be our client part. So, create a new project in Android Studio. After creating the project, we have to do one of the most important things.
&nbsp;

In Android Studio, select the Project view in the left top menu. Inside `app/src/main/`, create a folder called `aidl`.

![Alt Text](https://dev-to-uploads.s3.amazonaws.com/i/cpc4t7zilznasmno0eeh.png)
&nbsp;

After this, go to the AIDL service application, and copy the contents of `aidl` folder.
&nbsp;

![aidl2](https://dev-to-uploads.s3.amazonaws.com/i/ht0bxlgcj9a6o834a47y.png)

Switch back to the client application and paste the contents into `aidl` folder. `Build -> Rebuild`. The project should build without any errors.
&nbsp;

Go to MainActivity.java and create a `ServiceConnection` object and `IMyAidlInterface` instance.

```
private IMyAidlInterface iMyAidlInterface;
private final ServiceConnection mServiceConnection =
	new ServiceConnection() {
		@Override
		public void onServiceConnected(
    				ComponentName name, IBinder service) {

			iMyAidlInterface =
					IMyAidlInterface.Stub.asInterface(service);

			Log.d(TAG, "Service Connected.");
	    	Toast.makeText(MainActivity.this, "Service Connected.", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			iMyAidlInterface = null;
			Toast.makeText(MainActivity.this, "Service Disconnected.", Toast.LENGTH_SHORT).show();
		}
	};
```
&nbsp;

In `onCreate()` of `MainActivity.java`, bind the service. Note that we need the action and the package name to successfully bind to the service.

&nbsp;

```
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	Intent intent = new Intent();
	intent.setPackage("com.zeliot.aidldemo");
	intent.setAction("MyService");
	bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
}
```

Now, create a button and while clicking the button, call `iMyAidlInterface.getMessage()` to receive the String from the AIDL service.
&nbsp;

We are done with client and server parts. As of now, the Service is connected from another application. We can do any type of operations using the same. We can have data trasnfer,aynchronous calls, callbacks. More detailed implementation will be released in the second part of this series.
&nbsp;

**[AIDL source code](https://github.com/prasan29/aidl-blog)**
&nbsp;

## Thank you, for reading. Happy interfacing!! :grinning: :slightly_smiling_face:

&nbsp;
