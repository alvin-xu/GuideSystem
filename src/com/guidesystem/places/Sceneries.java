package com.guidesystem.places;

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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Sceneries extends Activity {
	private ListView listView;
	private List<Map<String, Object>> datas;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sceneries_list);
		listView = (ListView) findViewById(R.id.sceneries);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "" + 9);

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
						data.put("scenery_name", object.get("name"));
						data.put("scenery_score", object.get("score"));
						data.put("scenery_evals", object.get("evalNum"));
						data.put("scenery_brief", object.get("description"));
						datas.add(data);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				SimpleAdapter adapter = new SimpleAdapter(Sceneries.this,
						datas, R.layout.scenery_item, new String[] {
								"scenery_name", "scenery_brief" }, new int[] {
								R.id.scenery_name, R.id.scenery_brief });
				listView.setAdapter(adapter);
			}

			@Override
			public void onFail(JSONObject result) {

			}
		}, params);

		sceneryTask.execute("");
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				HashMap<String, String> map=(HashMap<String, String>) listView.getItemAtPosition(position);
				String sceneryId=map.get("scenery_score");
				Log.d("scenery", sceneryId);
				Intent i=new Intent(Sceneries.this, SceneryDetail.class);
				i.putExtra("sceneryId", sceneryId);
				startActivity(i);
			}
			
		});
	}

}
