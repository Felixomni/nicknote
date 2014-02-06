package com.felixware.nicknote.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class DayView extends TextView {
	private boolean isDaySelected;

	public DayView(Context context) {
		this(context, null, 0);
	}

	public DayView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DayView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public void setDaySelected(boolean selected) {
		isDaySelected = selected;
		if (selected) {
			this.setTextColor(getResources().getColor(android.R.color.black));
		} else {
			this.setTextColor(getResources().getColor(android.R.color.darker_gray));
		}
	}

	public boolean isDaySelected() {
		return isDaySelected;
	}

	public void toggle() {
		isDaySelected = !isDaySelected;
		setDaySelected(isDaySelected);
	}

}
