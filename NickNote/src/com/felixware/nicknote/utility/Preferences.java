package com.felixware.nicknote.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
	private static final String NICK_PREFERENCE = "nickPreferences";
	private static final String KEY_USERNAME = "prefUsername";
	private static final String KEY_PASSWORD = "prefPassword";
	private static final String KEY_RECIPIENTS = "prefRecipients";
	private static final String KEY_SCHEDULE_START_HOURS = "prefScheduleStartHours";
	private static final String KEY_SCHEDULE_START_MINUTES = "prefScheduleStartMinutes";
	private static final String KEY_SCHEDULE_END_HOURS = "prefScheduleEndHours";
	private static final String KEY_SCHEDULE_END_MINUTES = "prefScheduleEndMinutes";
	private static final String KEY_SCHEDULE_DAYS = "prefScheduleDays";

	public static void setUsername(Context context, String username) {
		saveString(context, KEY_USERNAME, username);
	}

	public static String getUsername(Context context) {
		return getString(context, KEY_USERNAME, "");
	}

	public static void setPassword(Context context, String password) {
		saveString(context, KEY_PASSWORD, password);
	}

	public static String getPassword(Context context) {
		return getString(context, KEY_PASSWORD, "");
	}

	public static void setRecipients(Context context, String recipients) {
		saveString(context, KEY_RECIPIENTS, recipients);
	}

	public static String getRecipients(Context context) {
		return getString(context, KEY_RECIPIENTS, "");
	}

	public static void setScheduleStartHours(Context context, int hours) {
		saveInt(context, KEY_SCHEDULE_START_HOURS, hours);
	}

	public static int getScheduleStartHours(Context context) {
		return getInt(context, KEY_SCHEDULE_START_HOURS, -1);
	}

	public static void setScheduleStartMinutes(Context context, int minutes) {
		saveInt(context, KEY_SCHEDULE_START_MINUTES, minutes);
	}

	public static int getScheduleStartMinutes(Context context) {
		return getInt(context, KEY_SCHEDULE_START_MINUTES, -1);
	}

	public static void setScheduleEndHours(Context context, int hours) {
		saveInt(context, KEY_SCHEDULE_END_HOURS, hours);
	}

	public static int getScheduleEndHours(Context context) {
		return getInt(context, KEY_SCHEDULE_END_HOURS, -1);
	}

	public static void setScheduleEndMinutes(Context context, int minutes) {
		saveInt(context, KEY_SCHEDULE_END_MINUTES, minutes);
	}

	public static int getScheduleEndMinutes(Context context) {
		return getInt(context, KEY_SCHEDULE_END_MINUTES, -1);
	}

	public static void setScheduleDays(Context context, boolean[] days) {
		saveString(context, KEY_SCHEDULE_DAYS, Utility.serializeDays(days));
	}

	public static boolean[] getScheduleDays(Context context) {
		return Utility.deserializeDays(getString(context, KEY_SCHEDULE_DAYS, null));
	}

	private static void saveString(Context context, String key, String data) {
		SharedPreferences prefs = context.getSharedPreferences(NICK_PREFERENCE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key, data);
		editor.commit();
	}

	private static String getString(Context context, String key, String defaultValue) {
		SharedPreferences prefs = context.getSharedPreferences(NICK_PREFERENCE, Context.MODE_PRIVATE);
		return prefs.getString(key, defaultValue);
	}

	private static void saveInt(Context context, String key, int data) {
		SharedPreferences prefs = context.getSharedPreferences(NICK_PREFERENCE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(key, data);
		editor.commit();
	}

	private static int getInt(Context context, String key, int defaultValue) {
		SharedPreferences prefs = context.getSharedPreferences(NICK_PREFERENCE, Context.MODE_PRIVATE);
		return prefs.getInt(key, defaultValue);
	}

}
