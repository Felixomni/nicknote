package com.felixware.nicknote.mail;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

import android.os.AsyncTask;

public class MailAsyncTask extends AsyncTask<String, Void, MailAsyncTask.MailError> {
	private String username, password;
	private Mail mail;
	private MailTaskCallback callback;

	public MailAsyncTask(String username, String password, Mail mail, MailTaskCallback callback) {
		this.username = username;
		this.password = password;
		this.mail = mail;
		this.callback = callback;
	}

	@Override
	protected MailError doInBackground(String... params) {
		try {
			GmailSender sender = new GmailSender(username, password);
			sender.sendMail(mail.getSubject(), mail.getBody(), mail.getSender(), mail.getRecipients());
		} catch (AuthenticationFailedException e) {
			return MailError.AUTHENTICATION;
		} catch (MessagingException e) {
			return MailError.CONNECTION;
		} catch (Exception e) {
			return MailError.OTHER;
		}
		return null;
	}

	@Override
	protected void onPostExecute(MailError error) {
		if (callback != null) {
			if (error != null) {
				callback.onFailure(error);
			} else {
				callback.onSuccess();
			}
		}
	}

	public enum MailError {
		CONNECTION,
		AUTHENTICATION,
		OTHER;
	}

	public interface MailTaskCallback {
		public void onSuccess();

		public void onFailure(MailError error);
	}
}