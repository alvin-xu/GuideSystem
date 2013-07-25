package com.guidesystem;

import com.guidesystem.login.R;
import com.guidesystem.map.GuideMap;
import com.guidesystem.places.Sceneries;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	private TabHost tabHost;
	private RadioGroup radioGroup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tabHost=getTabHost();
		tabHost.addTab(tabHost.newTabSpec("map").setIndicator("Map").setContent(new Intent(this, GuideMap.class)));
		tabHost.addTab(tabHost.newTabSpec("scenery").setIndicator("Scenery").setContent(new Intent(this, Sceneries.class)));
		radioGroup=(RadioGroup) findViewById(R.id.radiogroup);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.radio_maps:
					tabHost.setCurrentTabByTag("map");
					break;
				case R.id.radio_scenery:
					tabHost.setCurrentTabByTag("scenery");
					break;
				default:
					break;
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
