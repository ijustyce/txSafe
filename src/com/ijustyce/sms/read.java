/**
 * date: 2013-06-02
 * read message !
 */

package com.ijustyce.sms;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ijustyce.safe.R;
import com.ijustyce.safe.baseclass;
import com.txh.Api.sqlite;

public class read extends baseclass{

	String num , dbFile;
	boolean isexit = false;
	sqlite api;	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_show);
		api = new sqlite();
		dbFile = Constants.dbFile;	
		setmessage();
	}
	
	@SuppressWarnings("deprecation")
	private void setmessage(){
		int i ,j,count =1;
		DisplayMetrics dm = new DisplayMetrics(); 
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;

		SharedPreferences sharedPreferences = getSharedPreferences("read", Context.MODE_PRIVATE);
		num = sharedPreferences.getString("number", "");

		String [] phone = {num};
		String _id="",content="",ismy="",number="";

		Button bt = (Button) findViewById(R.id.newnote);
		bt.setText(getResources().getString(R.string.with) + tx.getName(num)[0]
				   + getResources().getString(R.string.main_show));
		bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkcolor));
		String[] column = {"_id","ismy","content"};
		String[][] value = api.getData(dbFile, "sms", "select * from sms where phone = ?", phone, column);
		if(value.length>15){
			tx.showToast(R.string.show_much);
		}
		if(value.length<1){
			tx.showToast(R.string.none_sms);
			startActivity(new Intent(this , conversation.class));
			this.finish();
		}
		for(i = value.length-1;i>-1;i--){
			for(j=0;j<value[i].length;j++){
				if(j==0){
					_id = value[i][j];
				}
				if(j==1){
					ismy = value[i][j];
				}
				if(j==2){
					content = value[i][j];
					number = tx.getName(num)[0];
					Button tv = new Button(this);
					tv.setLayoutParams(new LinearLayout.LayoutParams(7 * screenWidth / 8, 
										LinearLayout.LayoutParams.WRAP_CONTENT));
				
					tv.setId(Integer.parseInt(_id));
					if (ismy.equals("true")){
						number = getResources().getString(R.string.me);
					}
					tv.setTextSize(18);
					tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkcolor));
					tv.setTextColor(android.graphics.Color.BLACK);
					tv.setText(number + ":  " + content);
					tv.setContentDescription("sms_"+String.valueOf(count));
					tv.setOnLongClickListener(new OnLongClickListener(){

						public boolean onLongClick(View v) {
							Button bt = (Button)findViewById(v.getId());
							String smsTemp = bt.getText().toString();
							for(int i = 0 ;i<smsTemp.length();i++){
								String temp = smsTemp.substring(i,i+1);
								if(temp.equals(":")){
									show(smsTemp.substring(i+3, smsTemp.length() - 21), 
											String.valueOf(v.getId()));
									break;
								}
							}
							return false;
						}
						
					});
					TextView textview = new TextView(this);
					textview.setHeight(8);
					final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.message);
					linearLayout.addView(tv, 0);
					linearLayout.addView(textview, 0);
					count++;
				}
			}
		}
	}

	private void show(final String content , final String id){
		String delete = getResources().getString(R.string.delete).toString();
		String delAll = getResources().getString(R.string.del_all).toString();
		String copy = getResources().getString(R.string.copy).toString();
		String call = getResources().getString(R.string.call).toString();
		String transmit = getResources().getString(R.string.transmit).toString();
		String add = getResources().getString(R.string.add_contacts).toString();
		String option = getResources().getString(R.string.option).toString();
		String intecept = getResources().getString(R.string.intecept_num).toString();
		String[] menu = {delete , delAll, copy, transmit, call ,intecept, add};
		if(tx.getName(num)[1].equals("true")){
			menu = new String[]{delete , delAll, copy, transmit, call, intecept};
		}
		new AlertDialog.Builder(read.this)
				.setTitle(option)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setItems(menu,new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {

								// which is the id you select start from 0
								switch (which) {
								case 0:
									delete(id);
									break;
								case 1:
									clickClean();
									break;
								case 2:
									tx.setClipboard(content);
									break;
								case 3:
									transmit(content);
									break;
								case 4:
									call();
									break;
								case 5:
									add();
									break;
								case 6:
									addContacts();
								default:
									break;
								}
								dialog.dismiss();
							}
						}).show();
	}
	
	private void call(){
	
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + num));
		startActivity(intent);
		anim();
	}
	
	private void transmit(String content){
		
		Intent intent = new Intent(this , sendsms.class);
		Bundle bundle = new Bundle();
		bundle.putString("sms_body", content);
		intent.putExtras(bundle);
		startActivity(intent);
		this.finish();
		anim();
	}
	
	private void addContacts(){
		
	}

	private void delete(String id){
		
		String [] args = {id};
		api.delete(dbFile, "sms", "_id=?", args);
		
		boolean isExist;
		isExist = api.exists(dbFile, "sms", "phone", num);
		if(!isExist){
			String [] arg = {num};
			api.delete(dbFile, "phone", "phone=?", arg);
		}
		
		else{
			int j;
			String phone2="",content="",ismy="";
			String [] phone = {num};
			String[] column = {"phone","content","ismy"};
			String[][] value = api.getData(dbFile, "sms", "select * from sms where phone = ?", phone, column);
			for(j=0;j<value[0].length;j++){
				if(j==0){
					phone2 = value[value.length-1][j];
				}
				if(j==1){
					content = value[value.length-1][j];
				}
				if(j==2){
					ismy = value[value.length-1][j];
				}		
				String[]value2 = {phone2,content,ismy};
				String sql = "phone=?";
				api.update(dbFile, "phone", value2, column, phone, sql);
			}
		}
		
		Intent Intent = new Intent(this, read.class);
		startActivity(Intent);
		anim();
		this.finish();
	}

	private void del_total(){
		String [] arg = {num};
		api.delete(dbFile, "sms", "phone=?", arg);
		
		api.delete(dbFile, "phone", "phone=?", arg);
		
		Intent Intent = new Intent(this, conversation.class);
		startActivity(Intent);
		anim();
		this.finish();
	}

	public void yc(View v){

		switch (v.getId()){
			case R.id.newnote:
				SharedPreferences edit_new = getSharedPreferences("edit_new",
																  Context.MODE_PRIVATE);
				Editor my_edit = edit_new.edit();
				my_edit.putString("number", num);
				my_edit.commit();
				Intent newnote = new Intent(this, sendsms.class);
				startActivity(newnote);
				anim();
				this.finish();
		}
	}

	private void clickClean(){
		new AlertDialog.Builder(this)
		.setTitle(getResources().getString(R.string.ask))
		.setMessage(getResources().getString(R.string.clickCleanContent))
		.setNegativeButton(getResources().getString(R.string.no),
		new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog,int which){
			}
		})
		.setPositiveButton(getResources().getString(R.string.yes),
		new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog,int whichButton){
				del_total();
			}
		}).show();
	}

	private void add(){
		
		String[]column = {"value"};
		String[]value = {num};
		boolean isExist = api.exists(dbFile, "intercept", "value", num);
		if(isExist){
			Toast.makeText(this,getResources().getString(R.string.add_error),
					   Toast.LENGTH_LONG).show();
			return ;
		}
		else{
			api.insertData(dbFile, "intercept", value, column);
		}
	}
}