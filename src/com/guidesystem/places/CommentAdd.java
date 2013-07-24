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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class CommentAdd extends Activity{
	private RatingBar rank;
	private EditText comment;
	private Button submit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_add);
		
		rank=(RatingBar) findViewById(R.id.ratingBar1);
		comment=(EditText) findViewById(R.id.editText1);
		submit=(Button) findViewById(R.id.comment_add);
		
		rank.setRating(0);
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Log.d("comment", ""+rank+comment.getText().toString());
				
				if(rank.getRating()!=0 && !comment.getText().toString().equals("")){
					
					Map<String, String> params=new HashMap<String, String>();
					
					params.put("rank", ""+rank.getRating());
					params.put("comment", comment.getText().toString());
					
					HttpTask commentTask=new HttpTask(Constants.URL+"/scenery/addComment",new ResultCallBack() {
						
						@Override
						public void onSuccess(JSONObject result) {
							// TODO Auto-generated method stub
							try {
								if(result.getString("result").equals("success")){
									Toast.makeText(CommentAdd.this, "评论成功", Toast.LENGTH_SHORT).show();
									
								}
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
					commentTask.execute("");
				}else{
					Toast.makeText(CommentAdd.this, "请输入评论和分数", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
