package com.guidesystem.user;


import java.util.Random;

import com.guidesystem.login.R;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {

	private EditText phone;
	private EditText password;
	private EditText repassword;
	private EditText verification;
	private CheckBox tip;
	private View getVerif;
	private View register;
	private StringBuilder vString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		getViews();
		addListeners();
		register.setEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	private void getViews(){
		phone=(EditText)findViewById(R.id.phone);
		password=(EditText)findViewById(R.id.password);
		repassword=(EditText)findViewById(R.id.repassword);
		tip=(CheckBox)findViewById(R.id.tip);
		register=(Button)findViewById(R.id.register);
	}
	private void addListeners(){
		getVerif.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				vString=new StringBuilder();
				Random random=new Random();
				for(int i=0;i<4;i++){
					vString.append(random.nextInt(10));
				}
				Toast toast=Toast.makeText(Register.this, vString, Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
		});
		tip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1){
					register.setEnabled(true);
				}
				else{
					register.setEnabled(false);
				}
			}
		});
		register.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if((verification.getText().toString().trim()).equals(vString.toString())){
					Log.d("REGISTER", vString.toString());
					if(password.getText().toString().equals(repassword.getText().toString())){
						Toast toast=Toast.makeText(Register.this, "×¢²á³É¹¦", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
				}
			}
		});
		
	}
}
