package com.felixware.nicknote.mail;

public class Mail {
	private String subject;
	private String body;
	private String sender;
	private String recipients;

	public Mail(String subject, String body, String sender, String recipients) {
		this.subject = subject;
		this.body = body;
		this.sender = sender;
		this.recipients = recipients;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getRecipients() {
		return recipients;
	}

	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

}
