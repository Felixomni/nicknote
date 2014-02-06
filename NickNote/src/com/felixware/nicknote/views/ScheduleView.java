package com.felixware.nicknote.views;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.felixware.nicknote.R;
import com.felixware.nicknote.utility.Utility;

public class ScheduleView extends RelativeLayout {
	private int startHours, startMinutes, endHours, endMinutes;
	private EditText startTimeET, endTimeET;
	private DayView sunTV, monTV, tueTV, wedTV, thuTV, friTV, satTV;

	public ScheduleView(Context context) {
		this(context, null, 0);
	}

	public ScheduleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ScheduleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		LayoutInflater.from(context).inflate(R.layout.schedule_view, this);

		bindViews();

	}

	private void bindViews() {
		startTimeET = (EditText) findViewById(R.id.edittext_schedule_start);
		startTimeET.setOnClickListener(onTimeClickListener);

		endTimeET = (EditText) findViewById(R.id.edittext_schedule_end);
		endTimeET.setOnClickListener(onTimeClickListener);

		sunTV = (DayView) findViewById(R.id.textview_day_sun);
		sunTV.setOnClickListener(onDayClickListener);

		monTV = (DayView) findViewById(R.id.textview_day_mon);
		monTV.setOnClickListener(onDayClickListener);

		tueTV = (DayView) findViewById(R.id.textview_day_tue);
		tueTV.setOnClickListener(onDayClickListener);

		wedTV = (DayView) findViewById(R.id.textview_day_wed);
		wedTV.setOnClickListener(onDayClickListener);

		thuTV = (DayView) findViewById(R.id.textview_day_thu);
		thuTV.setOnClickListener(onDayClickListener);

		friTV = (DayView) findViewById(R.id.textview_day_fri);
		friTV.setOnClickListener(onDayClickListener);

		satTV = (DayView) findViewById(R.id.textview_day_sat);
		satTV.setOnClickListener(onDayClickListener);

	}

	public void setTimes(int startHours, int startMinutes, int endHours, int endMinutes) {
		this.startHours = startHours;
		this.startMinutes = startMinutes;
		this.endHours = endHours;
		this.endMinutes = endMinutes;
	}

	public void setStartTimeText(String text) {
		startTimeET.setText(text);
	}

	public void setEndTimeText(String text) {
		endTimeET.setText(text);
	}

	public int getStartHours() {
		return startHours;
	}

	public int getStartMinutes() {
		return startMinutes;
	}

	public int getEndHours() {
		return endHours;
	}

	public int getEndMinutes() {
		return endMinutes;
	}

	public boolean[] getDays() {
		return new boolean[] { sunTV.isDaySelected(), monTV.isDaySelected(), tueTV.isDaySelected(),
				wedTV.isDaySelected(), thuTV.isDaySelected(), friTV.isDaySelected(), satTV.isDaySelected() };
	}

	public void setDays(boolean[] days) {
		sunTV.setDaySelected(days[0]);
		monTV.setDaySelected(days[1]);
		tueTV.setDaySelected(days[2]);
		wedTV.setDaySelected(days[3]);
		thuTV.setDaySelected(days[4]);
		friTV.setDaySelected(days[5]);
		satTV.setDaySelected(days[6]);
	}

	private OnClickListener onTimeClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			final boolean isStartTime = (v.getId() == R.id.edittext_schedule_start);
			new TimePickerDialog(getContext(), new OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					if (isStartTime) {
						startHours = hourOfDay;
						startMinutes = minute;
						setStartTimeText(Utility.getTimeString(startHours, startMinutes));
						if (startHours > endHours || (startHours == endHours && startMinutes > endMinutes)) {
							endHours = startHours;
							endMinutes = (startMinutes != 59) ? startMinutes + 1 : 0;
							setEndTimeText(Utility.getTimeString(endHours, endMinutes));
						}
					} else {
						if (hourOfDay < startHours || (hourOfDay == startHours && minute < startMinutes)) {
							Toast.makeText(getContext(), "Scheduled end must be after schedule start!",
									Toast.LENGTH_SHORT).show();
						} else {
							endHours = hourOfDay;
							endMinutes = minute;
							setEndTimeText(Utility.getTimeString(endHours, endMinutes));
						}
					}
				}

			}, isStartTime ? startHours : endHours, isStartTime ? startMinutes : endMinutes, true).show();
		}
	};

	private OnClickListener onDayClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			((DayView) v).toggle();
		}

	};
}
