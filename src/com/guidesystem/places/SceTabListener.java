package com.guidesystem.places;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

public class SceTabListener<T extends Fragment> implements TabListener{
	private Fragment fragment;
	private Activity activity;
	private Class<T> mClass;
	
	public SceTabListener(Activity activity, Class<T> mClass) {
		this.activity = activity;
		this.mClass = mClass;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		if(fragment==null){
			fragment=Fragment.instantiate(activity, mClass.getName());
			ft.add(android.R.id.content, fragment,null);
		}
		ft.attach(fragment);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		if(fragment!=null){
			ft.detach(fragment);
		}
	}

}
