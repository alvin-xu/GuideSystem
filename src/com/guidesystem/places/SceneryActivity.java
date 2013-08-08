package com.guidesystem.places;

import com.guidesystem.common.AnimationTabHost;
import com.guidesystem.login.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.TabHost;
import android.widget.TabWidget;

@SuppressWarnings("deprecation")
public class SceneryActivity extends TabActivity{
	

	private TabHost tabHost;
	private TabWidget tabWidget;
	private GestureDetector gestureDetector;
	
//	private AnimationTabHost mTabHost;
	
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
//		tabHost=getTabHost();
		tabHost=(AnimationTabHost)findViewById(android.R.id.tabhost);
		
//		tabWidget=tabHost.getTabWidget();
		tabWidget=(TabWidget) findViewById(android.R.id.tabs);
		
//		LayoutInflater.from(this).inflate(R.layout.activity_scenery, tabHost.getTabContentView(), true);
		
		Intent detail=new Intent(this, SceneryDetail.class);
		Intent comment=new Intent(this, Comments.class);
		
		detail.putExtra("sceneryId", getIntent().getStringExtra("sceneryId"));
		comment.putExtra("sceneryId", getIntent().getStringExtra("sceneryId"));
		
		Log.d("scenery",getIntent().getStringExtra("sceneryId"));
		
		
		
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("æ∞µ„ΩÈ…‹").setContent(detail));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("∆¿¬€").setContent(comment));
		
		
		for(int i=0;i<tabWidget.getChildCount();i++){
			tabWidget.getChildAt(i).getLayoutParams().height=50;
		}
		
		gestureDetector=new GestureDetector(new MyGestureDetector());
		
		
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if(!gestureDetector.onTouchEvent(ev)){
			ev.setAction(MotionEvent.ACTION_CANCEL);
		}
		return super.dispatchTouchEvent(ev);
	}
	
	private class MyGestureDetector extends SimpleOnGestureListener{

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			if(e1.getX()-e2.getX()>120 && Math.abs(velocityX)>200){
				tabHost.setCurrentTab(1);
			}else if(e2.getX()-e1.getX()>120 && Math.abs(velocityX)>200){
				tabHost.setCurrentTab(0);
			}
			return true;
		}
		
	}
	
}
