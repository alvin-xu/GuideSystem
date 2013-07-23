package com.guidesystem.places;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.guidesystem.common.Constants;
import com.guidesystem.login.R;
import com.guidesystem.net.HttpTask;
import com.guidesystem.net.ResultCallBack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class SceneryDetail extends Activity{
	TextView nameView;
	TextView commentNums;
	TextView openTime;
	TextView stayTime;
	TextView ticket;
	TextView scenery_brief;
	
	RatingBar rankBar;
	
	Button addButton;
	Button commentButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scenery_detail);
		
		initViews();
		
		Map<String, String> params=new HashMap<String, String>();
//		Intent i=getIntent();
//		Log.d("scenery", i.getStringExtra("sceneryId"));
		params.put("id", ""+3);
		
		HttpTask sceneryTask=new HttpTask(Constants.URL+"/scenery/scenery_detail", new ResultCallBack() {
			
			@Override
			public void onSuccess(JSONObject result) {
				// TODO Auto-generated method stub
//				scenery\.name,scenery\.address,scenery\.description,
//				scenery\.playTime,scenery\.businessHour,
//				scenery\.ticket,scenery\.evalNum,scenery\.score
				try {
					Log.d("scenery",result.getString("name"));
					nameView.setText(result.getString("name"));
					commentNums.setText(result.getInt("evalNum"));
					openTime.setText(result.getString("businessHour"));
					stayTime.setText(result.getString("playTime"));
					ticket.setText(result.getString("ticket"));
					scenery_brief.setText(result.getString("description"));
					rankBar.setRating((float) result.getDouble("score"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFail(JSONObject result) {
				// TODO Auto-generated method stub
				
			}
		}, params);
		
		sceneryTask.execute("");
		
	}
	public void initViews(){
		nameView=(TextView) findViewById(R.id.scenery_name);
		commentNums=(TextView) findViewById(R.id.comment_nums);
		openTime=(TextView) findViewById(R.id.opentime);
		stayTime=(TextView) findViewById(R.id.staytime);
		ticket=(TextView) findViewById(R.id.ticket);
		scenery_brief=(TextView) findViewById(R.id.scenery_brief);
		
		rankBar=(RatingBar) findViewById(R.id.scenery_rank);
		
		addButton=(Button) findViewById(R.id.add_button);
		commentButton=(Button) findViewById(R.id.comment_button);
	}
	
}
