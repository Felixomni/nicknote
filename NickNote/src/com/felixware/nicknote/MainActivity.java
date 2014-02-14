package com.felixware.nicknote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.felixware.nicknote.mail.Mail;
import com.felixware.nicknote.mail.MailAsyncTask;
import com.felixware.nicknote.mail.MailAsyncTask.MailError;
import com.felixware.nicknote.utility.Preferences;
import com.felixware.nicknote.utility.Utility;
import com.felixware.nicknote.views.ScheduleView;
import com.felixware.nicknote.views.SettingCheckboxView;
import com.felixware.nicknote.views.SettingTextView;

/*
 * created by Kevin Healy on 4 February, 2014.
 */
public class MainActivity extends Activity {
	private SettingTextView userNameSTV, userPasswordSTV, recipientsSTV;
	private SettingCheckboxView detailedMessagesSCV;
	private ScheduleView scheduleSV;
	private boolean isSendingTestEmail = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		bindViews();

		setUpViews();
	}

	private void bindViews() {
		userNameSTV = (SettingTextView) findViewById(R.id.settingtextview_sender_email);

		userPasswordSTV = (SettingTextView) findViewById(R.id.settingtextview_sender_password);

		detailedMessagesSCV = (SettingCheckboxView) findViewById(R.id.settingcheckboxview_detailed_messages);

		recipientsSTV = (SettingTextView) findViewById(R.id.settingtextview_recipients);

		scheduleSV = (ScheduleView) findViewById(R.id.scheduleview);
	}

	private void setUpViews() {
		String username = Preferences.getUsername(this);
		String password = Preferences.getPassword(this);
		String recipients = Preferences.getRecipients(this);
		int startHours = Preferences.getScheduleStartHours(this);
		int startMinutes = Preferences.getScheduleStartMinutes(this);
		int endHours = Preferences.getScheduleEndHours(this);
		int endMinutes = Preferences.getScheduleEndMinutes(this);

		if (!TextUtils.isEmpty(username)) {
			userNameSTV.setInputText(username);
		}

		if (!TextUtils.isEmpty(password)) {
			userPasswordSTV.setInputText(password);
		}

		if (!TextUtils.isEmpty(recipients)) {
			recipientsSTV.setInputText(recipients);
		}

		detailedMessagesSCV.setCheckboxChecked(Preferences.getDetailedMessages(this));

		if (startHours != -1 && startMinutes != -1 && endHours != -1 && endMinutes != -1) {
			scheduleSV.setTimes(startHours, startMinutes, endHours, endMinutes);
			scheduleSV.setStartTimeText(Utility.getTimeString(startHours, startMinutes));
			scheduleSV.setEndTimeText(Utility.getTimeString(endHours, endMinutes));
		}

		scheduleSV.setDays(Preferences.getScheduleDays(this));
	}

	public void OnSaveClicked(View v) {
		Preferences.setUsername(this, userNameSTV.getInputText());
		Preferences.setPassword(this, userPasswordSTV.getInputText());
		Preferences.setRecipients(this, recipientsSTV.getInputText());
		Preferences.setDetailedMessages(this, detailedMessagesSCV.isCheckboxChecked());
		Preferences.setScheduleStartHours(this, scheduleSV.getStartHours());
		Preferences.setScheduleStartMinutes(this, scheduleSV.getStartMinutes());
		Preferences.setScheduleEndHours(this, scheduleSV.getEndHours());
		Preferences.setScheduleEndMinutes(this, scheduleSV.getEndMinutes());
		Preferences.setScheduleDays(this, scheduleSV.getDays());
	}

	public void OnTestClicked(View v) {
		String username = Preferences.getUsername(this);
		String password = Preferences.getPassword(this);
		String recipients = Preferences.getRecipients(this);

		if (!isSendingTestEmail) {
			if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(recipients)) {
				Toast.makeText(this, getString(R.string.setting_error_fields), Toast.LENGTH_SHORT).show();
			} else {
				isSendingTestEmail = true;
				Mail mail = new Mail(getString(R.string.test_email_subject), getString(R.string.test_email_body),
						username, recipients);
				new MailAsyncTask(username, password, mail, new MailAsyncTask.MailTaskCallback() {

					@Override
					public void onSuccess() {
						isSendingTestEmail = false;
						Toast.makeText(MainActivity.this, R.string.test_email_success, Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onFailure(MailError error) {
						isSendingTestEmail = false;
						createErrorDialog(error);
					}

				}).execute("");
			}
		}
	}

	public void createErrorDialog(MailError error) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String message;
		switch (error) {
		case AUTHENTICATION:
			message = getResources().getString(R.string.mail_error_authentication);
			break;
		case CONNECTION:
			message = getResources().getString(R.string.mail_error_no_connection);
			break;
		default:
			message = getResources().getString(R.string.mail_error_other);
			break;
		}
		builder.setTitle(getResources().getString(R.string.test_email_error)).setMessage(message)
				.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).setCancelable(false).create().show();
	}
}
