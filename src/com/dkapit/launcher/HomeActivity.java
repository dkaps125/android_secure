package com.dkapit.launcher;

import com.dkapit.launcher.R;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

public class HomeActivity extends FragmentActivity implements OnTouchListener {

	private SensorManager mSensorManager;
	private Sensor mSensor;
	private static int passIndex = 0;
	private static String[] pass = { "up", "down", "left", "right", "forward",
			"backward" };
	public static int x_cal = 0;
	public static int y_cal = 0;
	public static int z_cal = 0;
	public static boolean auth = false;
	
	static PowerManager pm; 
	static PowerManager.WakeLock wl;
		
	static boolean vol_pressed = false;
	
	static Context context;
	
	static Intent fakeShutDown;
	static Intent honeypot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen);
//		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//		mSensor = mSensorManager
//				.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
//		mSensorManager.registerListener(new Motion(), mSensor,
//				SensorManager.SENSOR_DELAY_NORMAL);
		auth = false;
		pm = (PowerManager) getSystemService(POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
		context = this;
		fakeShutDown = new Intent(context, PowerDown.class);
		honeypot = new Intent(this, HoneyPotActivity.class);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (HoneyPotActivity.honey_auth == false && 
				HoneyPotActivity.exited == 1) {
			UnauthorizedFragment frag = new UnauthorizedFragment();
			frag.show(getFragmentManager(), "Emergency Mode");
//			
//			Camera mycamera = Camera.open();
//			mycamera.enableShutterSound(false);
//			mycamera.takePicture(new Camera.ShutterCallback(), raw, jpeg)
		}
	}

//	class Motion implements SensorEventListener {
//		@Override
//		public void onAccuracyChanged(Sensor sensor, int accuracy) {
//		}
//
//		@Override
//		public void onSensorChanged(SensorEvent event) {
//			String moved = "";
//			if (event.values[0] < x_cal - 15) {
//				moved = "right";
//				Log.d("passcode", moved);
//			}
//
//			else if (event.values[0] > x_cal + 15) {
//				moved = "left";
//				Log.d("passcode", moved);
//			}
//
//			else if (event.values[1] < y_cal - 20) {
//				moved = "forward";
//				Log.d("passcode", moved);
//			}
//
//			else if (event.values[1] > y_cal + 20) {
//				moved = "back";
//				Log.d("passcode", moved);
//			}
//
//			else if (event.values[2] < z_cal - 15) {
//				moved = "up";
//				Log.d("passcode", moved);
//			}
//
//			else if (event.values[2] > z_cal + 15) {
//				moved = "down";
//				Log.d("passcode", moved);
//			}
//
//			if (moved.equalsIgnoreCase(pass[passIndex]))
//				passIndex++;
//			else
//				passIndex = 0;
//
//			if (passIndex == pass.length)
//				auth = true;
//		}
//	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			vol_pressed = true;
			Log.d("Message", vol_pressed + "");
		}
		return super.onKeyLongPress(keyCode, event);
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			vol_pressed = false;
			Log.d("Message", vol_pressed + "");
		}
		return super.onKeyLongPress(keyCode, event);
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}
}
