package com.guidesystem.places;

import com.guidesystem.login.R;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class SceneryActivity extends TabActivity{
	private TabHost tabHost;
	
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
//		setContentView(R.layout.activity_scenery);
		tabHost=getTabHost();
		
		LayoutInflater.from(this).inflate(R.layout.activity_scenery, tabHost.getTabContentView(), true);
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("kk",getResources().getDrawable(R.drawable.alert)).setContent(R.id.tab1));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("yy",getResources().getDrawable(R.drawable.alert)).setContent(R.id.tab2));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("zz",getResources().getDrawable(R.drawable.alert)).setContent(R.id.tab3));
//		
//		final ActionBar actionBar=getActionBar();
//		if(actionBar==null) Log.d("error", "actionBar==null");
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//		actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
//		actionBar.addTab(actionBar.newTab().setText("¾°µã").setTabListener(new SceTabListener<SceneriesFragment>(this,SceneriesFragment.class )));
//		actionBar.addTab(actionBar.newTab().setText("ÆÀÂÛ").setTabListener(new SceTabListener<CommentsFragment>(this,CommentsFragment.class )));
	}
	
}
