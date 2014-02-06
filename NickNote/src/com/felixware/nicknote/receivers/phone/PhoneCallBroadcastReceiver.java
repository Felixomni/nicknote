package com.felixware.nicknote.receivers.phone;

import java.util.Calendar;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateFormat;

import com.felixware.nicknote.R;
import com.felixware.nicknote.mail.Mail;
import com.felixware.nicknote.mail.MailAsyncTask;
import com.felixware.nicknote.utility.Preferences;
import com.felixware.nicknote.utility.Utility;

public class PhoneCallBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras == null)
			return;

		String state = extras.getString(TelephonyManager.EXTRA_STATE);
		if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
			String phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
			createAndSendEmail(context, phoneNumber);
		}
	}

	private void createAndSendEmail(Context context, String incomingNumber) {
		String username = Preferences.getUsername(context);
		String password = Preferences.getPassword(context);
		String recipients = Preferences.getRecipients(context);

		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(recipients)
				|| !Utility.isWithinScheduledTime(context)) {
			return;
		}

		String emailSubject = context.getString(R.string.phone_call_notification_subject);
		Date date = Calendar.getInstance().getTime();
		String timestamp = (String) DateFormat.format("hh:mm aa", date);
		String senderContact = Utility.getContactNameIfExists(context, incomingNumber);
		if (senderContact != null) {
			incomingNumber = senderContact;
		}
		String emailBody = String.format(context.getString(R.string.phone_call_notification_body), timestamp,
				incomingNumber);
		Mail mail = new Mail(emailSubject, emailBody, username, recipients);
		new MailAsyncTask(username, password, mail).execute("");
	}
}
