package com.guidesystem.places;

import com.guidesystem.login.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class SceneryActivity extends Activity{

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scenery);
		
		final ActionBar actionBar=getActionBar();
		if(actionBar==null) Log.d("error", "actionBar==null");
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
		actionBar.addTab(actionBar.newTab().setText("¾°µã").setTabListener(new SceTabListener<SceneriesFragment>(this,SceneriesFragment.class )));
		actionBar.addTab(actionBar.newTab().setText("ÆÀÂÛ").setTabListener(new SceTabListener<CommentsFragment>(this,CommentsFragment.class )));
	}
	
	
	
}
