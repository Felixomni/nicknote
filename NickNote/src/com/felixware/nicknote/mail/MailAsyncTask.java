package com.felixware.nicknote.mail;

import android.os.AsyncTask;
import android.util.Log;

public class MailAsyncTask extends AsyncTask<String, Void, String> {
	private String username, password;
	private Mail mail;

	public MailAsyncTask(String username, String password, Mail mail) {
		this.username = username;
		this.password = password;
		this.mail = mail;
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			GmailSender sender = new GmailSender(username, password);
			sender.sendMail(mail.getSubject(), mail.getBody(), mail.getSender(), mail.getRecipients());
		} catch (Exception e) {
			Log.e("SendMail", e.getMessage(), e);
		}
		return null;
	}
}