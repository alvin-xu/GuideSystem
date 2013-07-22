package com.guidesystem.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class HttpTask extends AsyncTask<Object, Integer, String>{
	
	private String url;
	private ResultCallBack resultCallBack;
	private Map<String, String> parameters;
	
	public HttpTask(String url, ResultCallBack resultCallBack,
			Map<String, String> params) {
		super();
		this.url = url;
		this.resultCallBack = resultCallBack;
		this.parameters = params;
	}

	@Override
	protected String doInBackground(Object... params) {

		HttpResponse response = null;
		HttpClient client=new DefaultHttpClient();
		HttpPost post=new HttpPost(url);
		List<NameValuePair> nvps=new ArrayList<NameValuePair>();
		
		if(parameters!=null && parameters.size()>0){
			for(Map.Entry<String, String> entry:parameters.entrySet()){
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
		try {
			post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			response=client.execute(post);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(response!=null && response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
			String result="";
			String temp=null;
			try {
				BufferedReader reader=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				while((temp=reader.readLine())!=null) result+=temp;
				Log.d("response", result);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}
		
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
	
		JSONObject object = null;
		try {
			if(result!=null){
				object = new JSONObject(result);
				Log.d("response", "result:"+object.getString("result"));
				if(object.getString("result").equals("success")){
					resultCallBack.onSuccess(object);
				}else{
					resultCallBack.onFail(object);
				}
			}else{
				resultCallBack.onFail(object);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
