package com.guidesystem.common;

import com.guidesystem.login.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AnimationTabHost extends TabHost{


	private Animation sideLeftIn;
	private Animation sideLeftOut;
	private Animation sideRightIn;
	private Animation sideRightOut;
	private int tabcount;

	public AnimationTabHost(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		sideLeftIn=AnimationUtils.loadAnimation(context, R.anim.side_left_in);
		sideLeftOut=AnimationUtils.loadAnimation(context, R.anim.side_left_out);
		sideRightIn=AnimationUtils.loadAnimation(context, R.anim.side_right_in);
		sideRightOut=AnimationUtils.loadAnimation(context, R.anim.side_right_out);
		tabcount=0;
	}
	
	@Override
	public void addTab(TabSpec tabSpec) {
		// TODO Auto-generated method stub
		tabcount++;
		super.addTab(tabSpec);
	}

	@Override
	public void setCurrentTab(int index) {
		// TODO Auto-generated method stub
		if(getCurrentView()!=null){
			
			if(index==1){
				Log.d("slide", "index 1");
				
				getCurrentView().startAnimation(sideLeftOut);
				
			}
			else if(index==0){
				Log.d("slide", "index 0");
				getCurrentView().startAnimation(sideLeftIn);
			} 	
		}
		super.setCurrentTab(index);
	}
}



