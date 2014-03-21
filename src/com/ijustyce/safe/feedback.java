
package com.ijustyce.safe;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import com.txh.Api.libs;

public class feedback extends baseclass{
	
	private ProgressDialog dialog;
	private EditText contact,content;
	private String msg , info;
	private txApplication tx;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		
		DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        
        int height = metric.heightPixels;
        
        EditText content = (EditText)findViewById(R.id.msg);
        content.setHeight(7*height/12);
       
        tx = (txApplication)getApplication();
	}
	
	public void btClick(View v){
		startSave();
	}
	
	private void startSave(){

		String dialog_title = getResources().getString(R.string.dialog_title);
		String dialog_content = getResources().getString(R.string.dialog_content);
		
		content = (EditText)findViewById(R.id.msg);
		contact = (EditText)findViewById(R.id.contact);
		
		msg = content.getText().toString();
		info = " qq: "+contact.getText().toString();
		
		if(msg.equals("")){
			
			tx.showToast(R.string.error_feedback);
			return ;
		}
		
		dialog = ProgressDialog.show(this, dialog_title, dialog_content, true);
		Thread thread = new Thread(null, domywork, "Background");
		thread.start();
	}

	@SuppressLint("HandlerLeak")
	private Runnable domywork = new Runnable(){

		public void run(){
			
			Looper.prepare();

			libs lib = new libs();
			String phone = "apk: "+tx.getVersion()+" phone: "+tx.getNumber();
			
			String[] args = {"justyce2013@163.com","MM|2013@163.com","1558789954@qq.com",
					phone+info,msg};
			lib.sendMail(args);
			
			handler.sendEmptyMessage(0);
			
			Looper.loop();
		}

		/* deal dialog! */
		Handler handler = new Handler(){
			public void handleMessage(Message msg){

				/* because there just a message */
				dialog.dismiss();
				empty();
			}
		};
	};
	
	private void empty(){
		content.setText("");
		contact.setText("");
	}
}
