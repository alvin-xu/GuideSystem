package com.guidesystem.login;


import java.util.HashMap;
import java.util.Map;


//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import com.guidesystem.map.GuideMap;
import com.guidesystem.net.HttpTask;
import com.guidesystem.net.ResultCallBack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.guidesystem.common.Constants.*;

public class Login extends Activity {
    /** Called when the activity is first created. */
	
//	private static final String url="http://192.168.3.101:8080/JSONServer/json";
//	
//	private HttpClient httpClient;
//	private HttpResponse response;
//	private HttpPost httpPost;
//	private HttpEntity entity;
	
	private Button loginButton;
	private Button registerButton;
	private Button cancelButton;
	private Button enterButton;
	
	private EditText usernameText;
	private EditText passwordText;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        loginButton=(Button)findViewById(R.id.LoginButton);
    	registerButton=(Button)findViewById(R.id.RegisterButton);
    	cancelButton=(Button)findViewById(R.id.CancelButton);
    	enterButton=(Button)findViewById(R.id.enterButton);
    	
    	usernameText=(EditText)findViewById(R.id.login_username);
    	passwordText=(EditText)findViewById(R.id.login_password);
    	
    	loginButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(Login.this, "LoginButton", Toast.LENGTH_SHORT).show();

				String username=usernameText.getText().toString();
				String password=passwordText.getText().toString();
	
//				Client client=new UserClient(Login.this);
//				client.Login(username.trim(), password.trim());
				Map<String , String> params=new HashMap<String, String>();
				params.put("userName", username.trim());
				params.put("password", password.trim());
				HttpTask loginTask=new HttpTask(URL+"/user/login.action", new ResultCallBack() {
					
					@Override
					public void onSuccess(JSONObject result) {
						// TODO Auto-generated method stub
						try {
							Toast.makeText(Login.this, "result"+result.getString("result"), Toast.LENGTH_SHORT).show();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					@Override
					public void onFail(JSONObject result) {
						// TODO Auto-generated method stub
						Toast.makeText(Login.this, "fail to login", Toast.LENGTH_SHORT).show();
					}
				}, params);
				loginTask.execute("Login");
				
			}
		});
        registerButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(Login.this, "RegisterButton", Toast.LENGTH_SHORT).show();
				
				Intent register=new Intent(Login.this, Register.class);
				startActivity(register);
			}
		});
        cancelButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
		       
		/*		new Thread(new  Runnable() {
					public void run() {
						Looper.prepare();
						  try {
					        	URL url=new URL(Login.url+"?param=urlconnection");
//								HttpsURLConnection urlConnection=(HttpsURLConnection)url.openConnection();
//								Your urlString must begin with https:// and not http:// for you to be able to cast it to a HttpsURLConnection.
								URLConnection urlConnection=url.openConnection();
								InputStreamReader in=new InputStreamReader(urlConnection.getInputStream());
								BufferedReader bufferedReader=new BufferedReader(in);
								String result="";
								String readLine=null;
								while((readLine=bufferedReader.readLine())!=null)	result+=readLine;
								in.close();
//								urlConnection.disconnect();
								Toast.makeText(Login.this, "result: "+result, Toast.LENGTH_LONG).show();
							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				}).start();
		      
		        
				Toast.makeText(Login.this, "CancelButton", Toast.LENGTH_SHORT).show();*/
			}
		});
        
        enterButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent map=new Intent(Login.this, GuideMap.class);
				startActivity(map);
			}
		});
        
       /* new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Looper.prepare();
				 String httpurl =Login.url+"?param=android";

		            //①httpget连接对象

		            HttpGet httpRequest = new HttpGet(httpurl);

		            //②取得httpclient的对象

		            HttpClient httpclient = new DefaultHttpClient();

		            try {
		            	Log.d("GET", "start get "+httpurl);
		              //③请求httpclient，取得httpResponse
		                HttpResponse httpResponse = httpclient.execute(httpRequest);

		         //④判断请求是否成功     if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){             //⑤取得返回的字符串
		                Log.d("GET", "get the get");
		                   String strResult = EntityUtils.toString(httpResponse.getEntity());

		                   Toast.makeText(Login.this, "httpclientget:"+strResult, Toast.LENGTH_LONG).show();


		            } catch (ClientProtocolException e) {

		                e.printStackTrace();

		            } catch (IOException e) {

		                e.printStackTrace();

		            }
			}
		}).start();*/
       
    }
  /*  @SuppressLint("ShowToast")
	private void login(int account,String password){
    	Log.d("login","start login");
    	httpClient=new DefaultHttpClient();
    	try{
    		httpPost=new HttpPost(url);
    		JSONObject data=new JSONObject();
    		try{
    			data.put("account", account);
    			data.put("password", password);
    		}catch (JSONException e) {
				// TODO: handle exception
			}
    		httpPost.setEntity(new StringEntity(data.toString()));
    		Log.d("login","request response");
    		response=httpClient.execute(httpPost);
    		Log.d("Login",""+response.getStatusLine().getStatusCode());
    		while(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
    			entity=response.getEntity();
    			StringBuffer sb=new StringBuffer();
    			BufferedReader reader=new BufferedReader(new InputStreamReader(entity.getContent()));
    			String s=null;
    			while((s=reader.readLine())!=null){
    				sb.append(s);
    			}
    			JSONObject datas=new JSONObject(sb.toString());
    			boolean result=datas.getBoolean("result");
    			System.out.println(datas.toString());
    			Log.d("LOgin", "login result:"+datas.toString());
    			if(result==true){
    				Log.d("LOgin", "login success");
    				Intent intent=new Intent(this,Register.class);
    				startActivity(intent);
    			}else{
    				Log.d("LOgin", "login fail");
    				Toast.makeText(this, "username not exist or password wrong", Toast.LENGTH_LONG);
    			}
    		}
    	}catch(Exception e){
    		
    	}
    }*/
}