package com.txh.Api;

import com.txh.mail.MultiMailsender;
import com.txh.mail.MultiMailsender.MultiMailSenderInfo;

public class libs {
	
	/**
	 * {userName,password,toAddress,subject,content}
	 * @param args
	 */
	
	public void sendMail(String[] args){
		MultiMailSenderInfo mailInfo = new MultiMailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName(args[0]);
		mailInfo.setPassword(args[1]);// ������������
		mailInfo.setFromAddress(args[0]);
		mailInfo.setToAddress(args[2]);
		mailInfo.setSubject(args[3]);
		mailInfo.setContent(args[4]);

		/*
		 * send more than one server
		 * 
		 * String[] receivers = new String[] { "***@163.com", "***@tom.com" };
		 * String[] ccs = receivers; mailInfo.setReceivers(receivers);
		 * mailInfo.setCcs(ccs);
		 */

		// �������Ҫ�������ʼ�

		MultiMailsender sms = new MultiMailsender();
		sms.sendTextMail(mailInfo);// ���������ʽ
		// MultiMailsender.sendMailtoMultiCC(mailInfo);// ���ͳ���*/
	}
}
