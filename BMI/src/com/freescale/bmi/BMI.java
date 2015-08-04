package com.freescale.bmi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.text.DecimalFormat;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class BMI extends Activity {
	static final String[] countries=new String[]{
		"China","Russia","Germany",
		"Ukraine","Belarus","USA","China1","China2","Germany",
		"Russia2","Belarus","USA"
	};
	
	protected static final int MENU_About=Menu.FIRST;
	protected static final int MENU_Quit=Menu.FIRST+1;
	protected static final int MENU_Test=Menu.FIRST+2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button button = (Button) findViewById(R.id.submit);
		button.setOnClickListener(calcBMI);
		
		Button button_jump=(Button)findViewById(R.id.jump);
		button_jump.setOnClickListener(ActivityJump);
		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, countries);
		AutoCompleteTextView textView=(AutoCompleteTextView)findViewById(R.id.auto_complete);
		textView.setAdapter(adapter);
	}
	
	private OnClickListener ActivityJump=new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(BMI.this, MyNextActivity.class);
			BMI.this.startActivity(intent);
		}
		
	};
	
	private OnClickListener calcBMI = new OnClickListener() {
		public void onClick(View v){
			try{
				Intent intent=BMI.this.getIntent();
				Bundle bundle=intent.getExtras();
				DecimalFormat df=new DecimalFormat("0.00");
				double h=Double.parseDouble(bundle.getString("height1"))/100;
				double w=Double.parseDouble(bundle.getString("weight1"));
				((EditText)findViewById(R.id.height)).setText(bundle.getString("height1"));
				((EditText)findViewById(R.id.weight)).setText(bundle.getString("weight1"));
				
	/*			DecimalFormat df=new DecimalFormat("0.00");
				EditText height=(EditText)findViewById(R.id.height);
				EditText weight=(EditText)findViewById(R.id.weight);
				double h=Double.parseDouble(height.getText().toString())/100;
				double w=Double.parseDouble(weight.getText().toString());*/
				double BMI=w/(h*h);
				TextView result=(TextView)findViewById(R.id.result);
				result.setText("Your BMI is "+df.format(BMI));
				//Give health advice
				TextView suggest=(TextView)findViewById(R.id.suggest);
				if(BMI>25){
					suggest.setText(R.string.advice_heavy);
					suggest.setTextColor(Color.RED);
				}else if(BMI<20){
					suggest.setText(R.string.advice_light);
					suggest.setTextColor(Color.YELLOW);
				}else{
					suggest.setText(R.string.advice_average);
					suggest.setTextColor(Color.BLUE);
				}
				//openOptionsDialog();
			}catch(Exception e){
				Toast.makeText(BMI.this, R.string.input_error, Toast.LENGTH_LONG).show();
			}
			
		}
	};
	
	
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		menu.add(0,MENU_About,0,"关于...");
		menu.add(0,MENU_Quit,0,"结束");
		menu.add(0,MENU_Test,0,"Test");
		return true;
	}
	
	
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
		case MENU_About:
			openOptionsDialog();
			break;
		case MENU_Quit:
			finish();
			break;
		case MENU_Test:
			TestDialog();
		}
		return true;
	}
	
	private void TestDialog(){
		Dialog dialog=new AlertDialog.Builder(this).setIcon(android.R.drawable.btn_star)
				.setTitle("LoveStarInvestigation").setMessage("Do you like Michael Jordan?")
				.setNeutralButton("JustSoSo", 
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										Toast.makeText(BMI.this, "I like Kobe Bryant most!", Toast.LENGTH_LONG).show();
									}
								})
				.setNegativeButton("NotLike", 
								new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										Toast.makeText(BMI.this, "I don't like the style of him!", Toast.LENGTH_LONG).show();
									}
								})
				.setPositiveButton("VeryLike",
								new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										Toast.makeText(BMI.this, "He is a great basketball player!", Toast.LENGTH_LONG).show();
									}
								})
								.create();
		dialog.show();
	}
	
	private void openOptionsDialog(){
		
		//Dialog Operation
		new AlertDialog.Builder(this).setTitle(R.string.about_title)
		.setMessage(R.string.about_msg)
		.setNegativeButton(R.string.homepage_label, 
		    	new DialogInterface.OnClickListener() {
						
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Uri uri=Uri.parse("http://www.baidu.com");
						Intent intent=new Intent(Intent.ACTION_VIEW,uri);
						startActivity(intent);
						
					}
				})
		.setPositiveButton(R.string.ok_label, 
				new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
		
					}
		        }).show();
		
	}
}
