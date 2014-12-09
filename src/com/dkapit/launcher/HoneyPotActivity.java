package com.dkapit.launcher;

import com.dkapit.launcher.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class HoneyPotActivity extends Activity {

	static boolean honey_auth;
	static int exited = 0;
	private static boolean calibrated;
	private SensorManager mSensorManager;
	private Sensor mSensor;

	static float x_norm;
	static float y_norm;
	static float z_norm;

	static float x_val;
	static float y_val;
	static float z_val;

	private static int passIndex;
	private static String[] pass = { "up", "down", "left", "right", "forward",
			"back" };
	
	Intent incoming;
	String outgoing;
	
	EditText passcode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.honeypot);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager
				.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		mSensorManager.registerListener(new Motion(), mSensor,
				SensorManager.SENSOR_DELAY_NORMAL);

		honey_auth = false;
		calibrated = false;
		exited = 1;
		passIndex = 0;
		incoming = getIntent();
		outgoing = incoming.getStringExtra("com.dkapit.launcher.AppsGridFragment");
		
		passcode = (EditText) findViewById(R.id.passcode);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager
				.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		mSensorManager.registerListener(new Motion(), mSensor,
				SensorManager.SENSOR_DELAY_NORMAL);

		honey_auth = false;
		calibrated = false;
		exited = 1;
		passIndex = 0;
		incoming = getIntent();
		outgoing = incoming.getStringExtra("com.dkapit.launcher.AppsGridFragment");
		
		passcode = (EditText) findViewById(R.id.passcode);
	}

	public void calibrate(View view) {
		x_norm = x_val;
		y_norm = y_val;
		z_norm = z_val;
		calibrated = true;
		exited = 1;
		passIndex = 0;
		Log.d("Passcode", "Calibrated! " + passIndex);
	}

	public void authenticate(View view) {
		EditText passcode = (EditText) findViewById(R.id.passcode);
		if (passIndex == 6 && honey_auth) {
			exited = 2;
			passIndex = 0;
			Intent intent = AppsGridFragment.aGrid.getActivity().getPackageManager().getLaunchIntentForPackage(outgoing);
			startActivity(intent);
		}

		else {
			passIndex = 0;
			passcode.setText("");
			Log.d("Passcode", "Reset!");
		}
	}

	class Motion implements SensorEventListener {

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSensorChanged(SensorEvent event) {

			x_val = event.values[0];
			y_val = event.values[1];
			z_val = event.values[2];
			if (passIndex < 6 && calibrated == true) {
				if (z_val < -z_norm - 10 && pass[passIndex].equals("up")) {
					passcode.setText("up");
					Log.d("Passcode", "Got it! " + passIndex);
					passIndex++;
				}

				else if (z_val > z_norm + 7 && pass[passIndex].equals("down")) {
					passcode.setText("down");
					Log.d("Passcode", "Got it! " + passIndex);
					passIndex++;
				}

				else if (x_val > x_norm + 10 && pass[passIndex].equals("left")) {
					passcode.setText("left");
					Log.d("Passcode", "Got it! " + passIndex);
					passIndex++;
				}

				else if (x_val < -x_norm - 10
						&& pass[passIndex].equals("right")) {
					passcode.setText("right");
					Log.d("Passcode", "Got it! " + passIndex);
					passIndex++;
				}

				else if (y_val < -y_norm - 15
						&& pass[passIndex].equals("forward")) {
					passcode.setText("forward");
					Log.d("Passcode", "Got it! " + passIndex);
					passIndex++;
				}

				else if (y_val > y_norm + 15 && pass[passIndex].equals("back")) {
					passcode.setText("back");
					Log.d("Passcode", "Got it! " + passIndex);
					passIndex++;
				}

				if (passIndex == 6) {
					honey_auth = true;
					Log.d("Passcode", "Complete! Text: " + passcode.getText());
				}
			}
		}
	}
	
	@Override
	public void onBackPressed() {}
}
