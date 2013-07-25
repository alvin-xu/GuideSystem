package com.guidesystem.places;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.guidesystem.common.Constants;
import com.guidesystem.login.R;
import com.guidesystem.net.HttpTask;
import com.guidesystem.net.ResultCallBack;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

public class Sceneries extends Activity {
	private ListView listView;
	private List<Map<String, Object>> datas;
	private Button refreshButton;
	Map<String, String> params;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sceneries_list);
		listView = (ListView) findViewById(R.id.sceneries);
		listView.setClickable(true);
		refreshButton=(Button) findViewById(R.id.refresh_button);
		
		params= new HashMap<String, String>();
		params.put("userName", "xbb");
		
		getSceneries();
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				String sceneryId=(String) datas.get(position).get("scenery_id");
				
				Log.d("scenery", sceneryId);
				
				Intent i=new Intent(Sceneries.this, SceneryActivity.class);
				i.putExtra("sceneryId", sceneryId);
				startActivity(i);
			}
			
		});
		
		refreshButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getSceneries();
			}
		});
	}
	
	public void getSceneries(){
		HttpTask sceneryTask = new HttpTask(Constants.URL
				+ "/scenery/showSceneries", new ResultCallBack() {

			@Override
			public void onSuccess(JSONObject result) {
				// TODO Auto-generated method stub
				 datas= new ArrayList<Map<String, Object>>();

				try {
					JSONArray jsons = result.getJSONArray("sceneries");
					for (int i = 0; i < jsons.length(); i++) {
						JSONObject object = jsons.getJSONObject(i);
						Map<String, Object> data = new HashMap<String, Object>();
						data.put("scenery_id", object.get("viewNo"));
						data.put("scenery_name", object.get("name"));
						data.put("scenery_score", object.get("score"));
						data.put("scenery_brief", object.get("description"));
						data.put("scenery_image", object.get("viewNo"));
						datas.add(data);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				SimpleAdapter adapter = new SimpleAdapter(Sceneries.this,
						datas, R.layout.scenery_item, new String[] {
								"scenery_name", "scenery_brief","scenery_score","scenery_image"}, new int[] {
								R.id.scenery_name, R.id.scenery_brief ,R.id.ratingBar1,R.id.imageView1});
				adapter.setViewBinder(new MyBinder());
				
				listView.setAdapter(adapter);
			}

			@Override
			public void onFail(JSONObject result) {

			}
		}, params);

		sceneryTask.execute("");
	}
	
	class MyBinder implements ViewBinder{

		@Override
		public boolean setViewValue(View view, Object data,
				String textRepresentation) {
			// TODO Auto-generated method stub
			if(view.getId()==R.id.ratingBar1){
				//json传的float类型变为double类型了！！！
				((RatingBar)view).setRating(Float.parseFloat(data.toString()));
				return true;
			}else if(view.getId()==R.id.imageView1){
				
				Log.d("scenery", data.toString());
				
				ImageView imaView=(ImageView)view;
				try {
					InputStream in=getResources().getAssets().open(data.toString()+"_0.jpg");
					if(in!=null){
						Log.d("scenery", "image is not null"+data.toString());
						imaView.setImageBitmap(BitmapFactory.decodeStream(in));
					}else{
						Log.d("scenery", "image is  null"+data.toString());
						imaView.setImageBitmap(BitmapFactory.decodeStream(getResources().getAssets().open("zhengchenggong.png")));
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
			
			return false;
		}
	
	}
}
