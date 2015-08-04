package com.michael.calc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	protected static final int MENU_About=Menu.FIRST;
	protected static final int MENU_Quit=Menu.FIRST+1;
	
	private Button[] btnNum = new Button[11];// 数值按钮  
    private Button[] btnCommand = new Button[5];// 符号按钮  
    private EditText editText = null;// 显示区域  
    private Button btnClear = null; // clear按钮  
    private String lastCommand; // 用于保存运算符  
    private boolean clearFlag; // 用于判断是否清空显示区域的值,true需要,false不需要  
    private boolean firstFlag; // 用于判断是否是首次输入,true首次,false不是首次  
    private double result; // 计算结果  
    
    public MainActivity() { 
        // 初始化各项值  
        result = 0; // x的值  
        firstFlag = true; // 是首次运算  
        clearFlag = false; // 不需要清空  
        lastCommand = "="; // 运算符  
    } 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取运算符  
        btnCommand[0] = (Button) findViewById(R.id.add); 
        btnCommand[1] = (Button) findViewById(R.id.subtract); 
        btnCommand[2] = (Button) findViewById(R.id.multiply); 
        btnCommand[3] = (Button) findViewById(R.id.divide); 
        btnCommand[4] = (Button) findViewById(R.id.equal); 
        // 获取数字  
        btnNum[0] = (Button) findViewById(R.id.num0); 
        btnNum[1] = (Button) findViewById(R.id.num1); 
        btnNum[2] = (Button) findViewById(R.id.num2); 
        btnNum[3] = (Button) findViewById(R.id.num3); 
        btnNum[4] = (Button) findViewById(R.id.num4); 
        btnNum[5] = (Button) findViewById(R.id.num5); 
        btnNum[6] = (Button) findViewById(R.id.num6); 
        btnNum[7] = (Button) findViewById(R.id.num7); 
        btnNum[8] = (Button) findViewById(R.id.num8); 
        btnNum[9] = (Button) findViewById(R.id.num9); 
        btnNum[10] = (Button) findViewById(R.id.point); 
        // 初始化显示结果区域  
        editText = (EditText) findViewById(R.id.result); 
        editText.setText("0.0"); 
        // 实例化监听器对象  
        NumberAction na = new NumberAction(); 
        CommandAction ca = new CommandAction(); 
        for (Button bc : btnCommand) { 
            bc.setOnClickListener(ca); 
        } 
        for (Button bc : btnNum) { 
            bc.setOnClickListener(na); 
        } 
        // clear按钮的动作  
        btnClear = (Button) findViewById(R.id.clear); 
        btnClear.setOnClickListener(new OnClickListener() { 
            @Override 
            public void onClick(View view) { 
                editText.setText("0.0"); 
                // 初始化各项值  
                result = 0; // x的值  
                firstFlag = true; // 是首次运算  
                clearFlag = false; // 不需要清空  
                lastCommand = "="; // 运算符  
            } 
        });
	}
	
	//number listener
	private class NumberAction implements OnClickListener{
		public void onClick(View view){
			Button btn=(Button)view;
			String input=btn.getText().toString();
			if(firstFlag){//first input
				if(input.equals(".")){
					return;
				}
				if(editText.getText().toString().equals("0.0")){
					editText.setText("");
				}
				firstFlag=false;
			}
			else{
				String editTextStr=editText.getText().toString();
				if(editTextStr.indexOf(".")!=-1 && input.equals("."))
					return;
				if(editTextStr.equals("-") && input.equals("."))
					return;
				if(editTextStr.equals("0") && !input.equals("."))
					return;
			}
			
			//if I have clicked the operator and then input number, it needs
			//to clear the value in the display filed.
			if(clearFlag){
				editText.setText("");
				clearFlag=false;
			}
			//set the value in the display filed
			editText.setText(editText.getText().toString()+input);
		}
	}
	
	//operator listener
	private class CommandAction implements OnClickListener{
		public void onClick(View view){
			Button btn=(Button)view;
			String inputCommmand=(String)btn.getText();
			if(firstFlag){
				if(inputCommmand.equals("-")){
					editText.setText("-");
					firstFlag=false;
				}
			}
			else{
				if(!clearFlag){//if clearFlag==false,it needs to calculate
					calculate(Double.parseDouble(editText.getText().toString()));
				}
				//store the operator you have clicked
				lastCommand=inputCommmand;
				clearFlag=true;
			}
		}
	}
	
	private void calculate(double x){
		if(lastCommand.equals("+")){
			result+=x;
		}else if(lastCommand.equals("-")){
			result-=x;
		}else if(lastCommand.equals("*")){
			result*=x;
		}else if(lastCommand.equals("/")){
			result/=x;
		}else if(lastCommand.equals("=")){
			result=x;
		}
		editText.setText(""+result);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		//super.onCreateOptionsMenu(menu);
		menu.add(0,MENU_About,0,"关于...");
		menu.add(0,MENU_Quit,0,"结束");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
		case MENU_About:
			openOptionsDialog();
			break;
		case MENU_Quit:
			finish();
			break;
		}
		return true;
	}
	
	private void openOptionsDialog(){
		new AlertDialog.Builder(this).setTitle("简易计算器")
		.setMessage("Author ： Michaelhuaqing \n Edition : 1.0")
		.setPositiveButton("确定", 
				new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				}).show();
	}
}
