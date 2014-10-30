package com.dkapit.launcher;

import com.dkapit.launcher.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class HomeActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen);
	}
	
	public void showApps(View v) {
		Intent i = new Intent(this, AppsListActivity.class);
		startActivity(i);
	}
}
