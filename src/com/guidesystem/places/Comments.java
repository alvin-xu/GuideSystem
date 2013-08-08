package com.guidesystem.places;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

import com.guidesystem.common.Constants;
import com.guidesystem.login.R;
import com.guidesystem.net.HttpTask;
import com.guidesystem.net.ResultCallBack;


public class Comments extends Activity {

	private int begin=0;
	private Button moreButton;
	private ListView listView;
	HashMap<String, String> params;
	List<Map<String, Object>> datas;
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comments_list);
		listView=(ListView) findViewById(R.id.comments_list);
		moreButton=(Button) findViewById(R.id.more_comments);
		
		params=new HashMap<String, String>();
		params.put("viewNo",getIntent().getStringExtra("sceneryId"));
		params.put("begin", ""+begin);
		
		datas=new ArrayList<Map<String,Object>>();
		
		getComments();
		
		moreButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("comment", "more button");
				getComments();
			}
		});
		overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
	}
	
	public void getComments(){
		
		params.remove("begin");
		params.put("begin", ""+begin);
		
		HttpTask commentTask=new HttpTask(Constants.URL+"/scenery/getComments.action", new ResultCallBack() {
			
			@Override
			public void onSuccess(JSONObject result) {
				// TODO Auto-generated method stub
				
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
						data.put("rank", map.get("score"));
						data.put("date", map.getString("date"));
						datas.add(data);
					}
				} catch (JSONException e) {
					// TODO: handle exception
				}
				SimpleAdapter adapter = new SimpleAdapter(Comments.this,datas,
						R.layout.comment_item, new String[] { "username", "rank",
								"comment", "date" }, new int[] { R.id.username,
								R.id.ratingBar_comment, R.id.comment, R.id.date });
				adapter.setViewBinder(new MyBinder());
				
				listView.setAdapter(adapter);
				
				begin+=Constants.COMM_NUM;
			}
			
			@Override
			public void onFail(JSONObject result) {
				// TODO Auto-generated method stub
				
			}
		},params);
		
		commentTask.execute("get comments");
	}
	
	class MyBinder implements ViewBinder{

		@Override
		public boolean setViewValue(View view, Object data,
				String textRepresentation) {
			// TODO Auto-generated method stub
			if(view.getId()==R.id.ratingBar_comment){
				//json传的float类型变为double类型了！！！
				((RatingBar)view).setRating(Float.parseFloat(data.toString()));
				return true;
			}
			return false;
		}
	}

}
