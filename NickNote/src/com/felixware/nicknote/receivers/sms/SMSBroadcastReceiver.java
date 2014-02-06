package com.felixware.nicknote.receivers.sms;

import java.util.Calendar;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.text.format.DateFormat;

import com.felixware.nicknote.R;
import com.felixware.nicknote.mail.Mail;
import com.felixware.nicknote.mail.MailAsyncTask;
import com.felixware.nicknote.utility.Preferences;
import com.felixware.nicknote.utility.Utility;

public class SMSBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras == null)
			return;

		Object[] pdus = (Object[]) extras.get("pdus");
		for (int i = 0; i < pdus.length; i++) {
			SmsMessage SMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
			String sender = SMessage.getOriginatingAddress();
			String body = SMessage.getMessageBody().toString();

			createAndSendEmail(context, sender, body);

		}

	}

	private void createAndSendEmail(Context context, String sender, String body) {
		String username = Preferences.getUsername(context);
		String password = Preferences.getPassword(context);
		String recipients = Preferences.getRecipients(context);

		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(recipients)
				|| !Utility.isWithinScheduledTime(context)) {
			return;
		}

		String emailSubject = context.getString(R.string.sms_notification_subject);
		Date date = Calendar.getInstance().getTime();
		String timestamp = (String) DateFormat.format("hh:mm aa", date);
		String senderContact = Utility.getContactNameIfExists(context, sender);
		if (senderContact != null) {
			sender = senderContact;
		}
		String emailBody = String.format(context.getString(R.string.sms_notification_body), timestamp, sender, body);
		Mail mail = new Mail(emailSubject, emailBody, username, recipients);
		new MailAsyncTask(username, password, mail).execute("");
	}
}
