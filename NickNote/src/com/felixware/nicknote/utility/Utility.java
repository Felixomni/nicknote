package com.felixware.nicknote.utility;

import java.util.Calendar;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.PhoneLookup;

public class Utility {

	public static String getContactNameIfExists(Context context, String phoneNumber) {
		String contactName = null;
		ContentResolver localContentResolver = context.getContentResolver();
		Cursor contactLookupCursor = localContentResolver.query(
				Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber)), new String[] {
						PhoneLookup.DISPLAY_NAME, PhoneLookup._ID }, null, null, null);
		try {
			while (contactLookupCursor.moveToNext()) {
				contactName = contactLookupCursor.getString(contactLookupCursor
						.getColumnIndexOrThrow(PhoneLookup.DISPLAY_NAME));

			}
		} finally {
			contactLookupCursor.close();
		}
		return contactName;
	}

	public static String getTimeString(int hour, int minute) {
		StringBuilder timeString = new StringBuilder();
		if (hour < 9) {
			timeString.append("0");
		}
		timeString.append(hour).append(":");
		if (minute < 9) {
			timeString.append("0");
		}
		timeString.append(minute);
		return timeString.toString();
	}

	public static boolean isWithinScheduledTime(Context context) {
		Calendar c = Calendar.getInstance();
		int startHours = Preferences.getScheduleStartHours(context);
		int startMinutes = Preferences.getScheduleStartMinutes(context);
		int endHours = Preferences.getScheduleEndHours(context);
		int endMinutes = Preferences.getScheduleEndMinutes(context);
		boolean[] days = Preferences.getScheduleDays(context);

		if (!days[c.get(Calendar.DAY_OF_WEEK) - 1]) { // DAY_OF_WEEK is 1-7, array is 0-6
			return false;
		}

		if ((startHours == -1 || startMinutes == -1 || endHours == -1 || endMinutes == -1)
				|| (startHours == endHours && startMinutes == endMinutes)) {
			return true;
		}

		int currentHour = c.get(Calendar.HOUR_OF_DAY);
		int currentMinute = c.get(Calendar.MINUTE);
		if ((currentHour > startHours || (currentHour == startHours && currentMinute > startMinutes))
				&& (currentHour < endHours || (currentHour == endHours && currentMinute < endMinutes))) {
			return true;
		} else {
			return false;
		}
	}

	public static String serializeDays(boolean[] days) {
		StringBuilder daysString = new StringBuilder();

		for (int i = 0; i < days.length; i++) {
			if (days[i]) {
				daysString.append("true");
			} else {
				daysString.append("false");
			}
			if (i != days.length - 1) {
				daysString.append(",");
			}
		}
		return daysString.toString();
	}

	public static boolean[] deserializeDays(String daysString) {
		if (daysString == null) {
			return new boolean[7];
		}
		String[] dayStringArray = daysString.split(",");

		boolean[] days = new boolean[dayStringArray.length];

		for (int i = 0; i < days.length; i++) {
			days[i] = (dayStringArray[i].equals("true"));
		}

		return days;
	}
}
