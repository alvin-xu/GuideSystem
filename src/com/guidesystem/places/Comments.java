package com.guidesystem.places;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleAdapter;

import com.guidesystem.common.Constants;
import com.guidesystem.login.R;
import com.guidesystem.net.HttpTask;
import com.guidesystem.net.ResultCallBack;


public class Comments extends ListActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("viewNo",getIntent().getStringExtra("sceneryId"));
		
		HttpTask commentTask=new HttpTask(Constants.URL+"/scenery/getComments.action", new ResultCallBack() {
			
			@Override
			public void onSuccess(JSONObject result) {
				// TODO Auto-generated method stub
				List<Map<String, Object>> datas=new ArrayList<Map<String,Object>>();
				try {
					JSONArray jsonArray=result.getJSONArray("data");
					
					Log.d("response", jsonArray.length()+"");
					
					for(int i=0;i<jsonArray.length();i++){
						JSONObject map=jsonArray.getJSONObject(i);
						
						Log.d("response","username"+map.getString("commentator"));
						Log.d("response","comment"+map.getString("content"));
						Log.d("response","score"+map.getInt("score"));
						Log.d("response","date"+map.getString("date"));
						
						Map<String, Object> data=new HashMap<String, Object>();
						data.put("username", map.getString("commentator"));
						data.put("comment", map.getString("content"));
						data.put("rank", map.getInt("score"));
						data.put("date", map.getString("date"));
						datas.add(data);
					}
				} catch (JSONException e) {
					// TODO: handle exception
				}
				SimpleAdapter adapter = new SimpleAdapter(Comments.this,datas,
						R.layout.comment_item, new String[] { "username", "rank",
								"comment", "date" }, new int[] { R.id.username,
								R.id.rank, R.id.comment, R.id.date });
				Comments.this.setListAdapter(adapter);
			}
			
			@Override
			public void onFail(JSONObject result) {
				// TODO Auto-generated method stub
				
			}
		},params);
		
		commentTask.execute("get comments");
		
	}

}
