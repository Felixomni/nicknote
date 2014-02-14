package com.felixware.nicknote.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.felixware.nicknote.R;

public class SettingCheckboxView extends RelativeLayout {
	private TextView nameText, labelText;
	private CheckBox settingCheckBox;

	public SettingCheckboxView(Context context) {
		this(context, null, 0);
	}

	public SettingCheckboxView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SettingCheckboxView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		LayoutInflater.from(context).inflate(R.layout.setting_checkbox_view, this);

		nameText = (TextView) findViewById(R.id.textview_setting_name);
		labelText = (TextView) findViewById(R.id.textview_setting_label);

		settingCheckBox = (CheckBox) findViewById(R.id.checkbox_setting);

		TypedArray attrsArray = context.obtainStyledAttributes(attrs, R.styleable.nicknoteattrs);
		String nameString = attrsArray.getString(R.styleable.nicknoteattrs_name_text);
		String labelString = attrsArray.getString(R.styleable.nicknoteattrs_label_text);

		attrsArray.recycle();

		if (!TextUtils.isEmpty(nameString)) {
			setNameText(nameString);
		}

		if (!TextUtils.isEmpty(labelString)) {
			setLabelText(labelString);
		}

	}

	public void setNameText(String name) {
		nameText.setText(name);
	}

	public void setLabelText(String label) {
		labelText.setText(label);
	}

	public void setCheckboxChecked(boolean checked) {
		settingCheckBox.setChecked(checked);
	}

	public boolean isCheckboxChecked() {
		return settingCheckBox.isChecked();
	}

}
