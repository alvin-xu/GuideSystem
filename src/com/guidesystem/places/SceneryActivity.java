package com.guidesystem.places;

import com.guidesystem.login.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
		
		Intent detail=new Intent(this, SceneryDetail.class);
		Intent comment=new Intent(this, Comments.class);
		
		detail.putExtra("sceneryId", getIntent().getStringExtra("sceneryId"));
		comment.putExtra("sceneryId", getIntent().getStringExtra("sceneryId"));
		
		Log.d("scenery",getIntent().getStringExtra("sceneryId"));
		
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("æ∞µ„ΩÈ…‹",null).setContent(detail));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("∆¿¬€",null).setContent(comment));
		
	}
	
}
