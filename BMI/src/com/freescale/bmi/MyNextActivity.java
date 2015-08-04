package com.freescale.bmi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

public class MyNextActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.next);
		
		Button button=(Button)findViewById(R.id.back);
		button.setOnClickListener(BackActivity);
	}
	
	private OnClickListener BackActivity=new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			EditText height=(EditText)findViewById(R.id.height1);
			EditText weight=(EditText)findViewById(R.id.weight1);
			//double h=Double.parseDouble(height.getText().toString());
			//double w=Double.parseDouble(weight.getText().toString());
			
			Bundle bundle=new Bundle();
			bundle.putString("height1", height.getText().toString());
			bundle.putString("weight1", weight.getText().toString());
	
			Intent intent=new Intent(MyNextActivity.this, BMI.class);
			intent.putExtras(bundle);
			MyNextActivity.this.startActivity(intent);
		}
		
	};

}
