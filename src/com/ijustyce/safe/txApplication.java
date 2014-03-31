package com.ijustyce.safe;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Process;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class txApplication extends Application {

	private static String tag = "txTag";
	public String sharedName = "shared";
	public boolean pw = false;
	public String head = "";
	private boolean chat = false;
	public boolean runing = false;
	public String table;
	private String incomingNumber;
	@Override
	public void onCreate() {
		
		getDbFile();
		Log.i(tag, "Application onCreate , pid = " + Process.myPid());
	}
	
	/**
	 * set theme and return theme name
	 * @param context
	 */
	public String theme(Context context){
		
		String themeString = getPreferences("theme", getSharedPreferencesName());
		
		if(themeString.equals("")){
			return "";
		}
		Log.i(tag, themeString);
		
		if (themeString.equals("sky")) {
			
			context.setTheme(R.style.txTheme3);
		} else if(themeString.equals("beauty")){
			
			context.setTheme(R.style.txTheme);
		}else if(themeString.equals("pure")){
			context.setTheme(R.style.txTheme2);
		}
		
		return themeString;
	}
	
	/**
	 * read preferences file and return value if possible or return null
	 * @param value 
	 * @param fileName
	 * @return
	 */
	public String getPreferences(String key ,String fileName){
		
		SharedPreferences shared = getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		
		return shared.getString(key, "null");
	}
	
	/**
	 *  set preferences file value
	 * @param key key of preference file 
	 * @param value value of key
	 * @param fileName preference file name
	 */
	public void setPreferences(String key , String value , String fileName){
		
		SharedPreferences shared = getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		
		shared.edit().putString(key, value).commit();
	}
	
	
	/**
	 * return PreferenceActivity's sharedPreferences
	 * @return sharedPreferences
	 */
	public SharedPreferences getSharedPreferences(){
		
		SharedPreferences shared = getSharedPreferences(getSharedPreferencesName(),
				Context.MODE_PRIVATE);
		return shared;
	}
	
	/**
	 * return PreferenceActivity's sharedPreferences
	 * @return pkgName_preferences
	 */
	public String getSharedPreferencesName(){
		
		return this.getPackageName() + "_preferences";
	}
	
	/**
	 * return sqlite file path ,if parent directory not exist
	 * it will create !
	 * @return
	 */
	public void getDbFile(){
	
		String file = this.getFilesDir().getPath();
		File f = new File(file);
		if(!f.exists()){
			f.mkdir();
		}
	}
	
	/**
	 * return windows animation string
	 * @return
	 */
	
	public String getAnim(){
		
		SharedPreferences myshared = getSharedPreferences();
		String anim = myshared.getString("anim", "ubuntu");
		Log.i(tag, "anim: "+anim);
		return anim;
	}
	
	/**
	 * find name by phone number , return phone number 
	 * and false if not exist , or return name and true
	 * @return
	 */
	public String[] getName(String number){
		
		String name = "";
		String temp = number;
		String[] result = new String[2];
		result[1] = "true";
		int error = 0;
		while(name.equals("")&&error<3){
			if(error ==1&&number.length()>10){
				temp = number.substring(0,3)+" "+number.substring(3,7)+
						" "+number.substring(7,11);
			}
			if(error ==2&&number.length()>10){
				temp = number.substring(0,3)+" "+number.substring(3,7)+" "+
			number.substring(7,11);
			}
			
			String[] projection = {
					ContactsContract.PhoneLookup.DISPLAY_NAME,
					ContactsContract.CommonDataKinds.Phone.NUMBER };
			Cursor cur = this.getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					projection, // Which columns to return.
					ContactsContract.CommonDataKinds.Phone.NUMBER
					+ " = '" + temp + "'", // WHERE clause.
					null, // WHERE clause value substitution
					null); // Sort order.
			if (cur == null){
				result[0] = number;
				result[1] = "false";
				return result;
			}
			for (int i = 0; i < cur.getCount(); i++){
				cur.moveToPosition(i);
				int nameFieldColumnIndex = cur
				.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
				name = cur.getString(nameFieldColumnIndex);
			}
			cur.close();
			error++;
		}
		if (name.equals("")){
			name = number;
			result[1] = "false";
		}
		result[0] = name;
		return result;
	}
	
	/**
	 * return pkgName+" "+versionName , if fail return ""
	 * @return
	 */
	public String getVersion() {
	    
		try {
	        PackageManager manager = this.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
	        String version = info.versionName;
	        String pkgName = info.applicationInfo.loadLabel
	        		(getPackageManager()).toString();
	        return pkgName+" "+version;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "";
	    }
	}
	
	/**
	 * return date , format like yyyy/MM/dd/HH/mm
	 * @return
	 */
	public String getDate(){
		 SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd/HH/mm",Locale.CHINA);
		 Date dd = new Date();
		 return ft.format(dd);
	}
	
	/**
	 * return date by your own formatter , like yyyy/MM/dd/HH/mm
	 * @return
	 */
	public String getDate(String formatter){
		
		SimpleDateFormat ft = new SimpleDateFormat(formatter,Locale.CHINA);
		 Date dd = new Date();
		 return ft.format(dd);
	}
	
	/**
	 * return time1-time2 as a millisecond value
	 * @param time1
	 * @param time2
	 * @return
	 */	
	public long getQuot(String time1, String time2){
		 long quot = 0;
		 SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd/HH/mm",Locale.CHINA);
		 try {
			 Date date1 = ft.parse(time1);
			 Date date2 = ft.parse(time2);
			 quot = date1.getTime() - date2.getTime();
		  } catch (ParseException e) {
		   e.printStackTrace();
		  }
		  return quot;
	}
	
	/**
	 * return call state
	 * @return
	 */
	public boolean getCallState(){
		return chat;
	}
	
	/**
	 * set call state
	 * @return
	 */
	public void setCallState(boolean state){
		chat = state;
	}
	
	/**
	 * show notification
	 * @param tickerText
	 * @param contentTitle
	 * @param contentText
	 * @param intent
	 */
	public void notifi(int icon , String tickerText , String contentTitle ,
			String contentText , PendingIntent intent) {
		
		NotificationManager mNotificationManager = (NotificationManager)
				this.getSystemService(Context.NOTIFICATION_SERVICE);
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon, tickerText, when);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		Context context = getApplicationContext();
        notification.setLatestEventInfo(context, contentTitle, contentText , intent); 
        mNotificationManager.notify(0, notification);
	}
	
	/**
	 * setting the number to intercept
	 * @param num
	 */
	
	public void setPhone(String num){
		incomingNumber = num;
	}
	
	/**
	 * return the number to intercept
	 * @return
	 */
	public String getPhone(){
		return incomingNumber;
	}
	
	/**
	 * @return phone number if success or return null 
	 */
	
	public String getNumber(){
	
		TelephonyManager mngr = (TelephonyManager)this.
				getSystemService(Context.TELEPHONY_SERVICE); 
		String number = mngr.getLine1Number();
	    
	    Log.i("---justyce---", "phone number :"+number);
	    return number;
	}
	
	/**
	 * show toast
	 * @param id name of string in string.xml , such as R.string.add
	 */
	public void showToast(int id) {

		String s = getResources().getString(id).toString();
		showToast(s);
	}
	
	/**
	 * get string of strings.xml
	 * @param id string id like R.string.hello
	 * @return string value
	 */
	public String getStringValue(int id){
		
		return getResources().getString(id).toString();
	}

	/**
	 * show toast
	 * @param s String to show  
	 */
	public void showToast(String s) {

		Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * set text to clipboard 
	 * @param text the text set to clipboard 
	 */
	public void setClipboard(String text){
		
		ClipboardManager clipboard = (ClipboardManager)getSystemService
				(Context.CLIPBOARD_SERVICE);
		
		clipboard.setText(text);
		showToast(R.string.copy_success);
	}
	
	/**
	 * get clipboard text
	 * @return clipboard text
	 */
	public String getClipboard(){
		
		ClipboardManager clipboard = (ClipboardManager)getSystemService
				(Context.CLIPBOARD_SERVICE);
		
		return clipboard.getText().toString();
	}
}