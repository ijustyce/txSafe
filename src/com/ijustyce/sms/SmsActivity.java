/**
 * date:2013-06-02
 * receiver message then call dealmsg
 */

package com.ijustyce.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsActivity extends BroadcastReceiver {

	String body = "";
	String number = "";

	@Override
	public void onReceive(Context context, Intent intent) {
		this.abortBroadcast();

		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			Object[] pdus = (Object[]) bundle.get("pdus");
			SmsMessage[] messages = new SmsMessage[pdus.length];
			for (int i = 0; i < pdus.length; i++) {
				messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
			}

			SmsMessage sms = messages[0];
			try {
				if (messages.length == 1 || sms.isReplace()) {
					body = sms.getDisplayMessageBody();
					Log.d("---sms---", body); 
				} else {
					StringBuilder bodyText = new StringBuilder();
					for (int i = 0; i < messages.length; i++) {
						bodyText.append(messages[i].getMessageBody());
						Log.d("---sms append��---", body); 
					}
					body = bodyText.toString();
				}
			} catch (Exception e) {

			}

			number = messages[0].getOriginatingAddress();
			if (number.startsWith("+86")) {
				number = number.replace("+86", "");
			}

			SharedPreferences new_sms = context.getSharedPreferences("new_sms",
					Context.MODE_PRIVATE);
			Editor new_edit = new_sms.edit();
			new_edit.putString("sms", body);
			new_edit.putString("phone", number);
			new_edit.commit();

			Intent showMsg = new Intent(context, dealmsg.class);
			showMsg.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startService(showMsg);

		}
	}
}
