package com.dkapit.launcher;

import java.io.DataOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

public class PowerDown extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		View decorView = getWindow().getDecorView();
		// Hide both the navigation bar and the status bar.
		// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
		// a general rule, you should design your app to hide the status bar whenever you
		// hide the navigation bar.
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
		              | View.SYSTEM_UI_FLAG_FULLSCREEN
		              | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
		decorView.setSystemUiVisibility(uiOptions);
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		params.screenBrightness = 0;
		getWindow().setAttributes(params);
		
		String[] cmds = {"sysrw", "rm /data/local/bootanimation.zip", "sysro"};
		
		try {
			Process p = Runtime.getRuntime().exec("su");
        	DataOutputStream os = new DataOutputStream(p.getOutputStream());            
        	for (String tmpCmd : cmds) {
                os.writeBytes(tmpCmd+"\n");
        	}           
        	os.writeBytes("exit\n");  
        	os.flush();
		} catch (IOException e) {
			Log.e("PowerOff", e.getMessage());
		} finally {
			PowerOffFragment dialog = new PowerOffFragment();
			dialog.show(this.getFragmentManager(), "Warning");
		}
	}
	
	@Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if ((event.getKeyCode() == KeyEvent.KEYCODE_HOME)) {
        	startActivity(HomeActivity.honeypot);
            return true;
        }
        else
            return super.dispatchKeyEvent(event);
    }
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if ((keyCode==KeyEvent.KEYCODE_BACK))
        {
        	startActivity(HomeActivity.honeypot);
            return true;
        }
           else
             return super.onKeyDown(keyCode, event);
    }
}
