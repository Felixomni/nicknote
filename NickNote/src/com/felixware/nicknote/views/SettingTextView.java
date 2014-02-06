package com.felixware.nicknote.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.felixware.nicknote.R;

public class SettingTextView extends RelativeLayout {
	protected TextView nameText;
	protected EditText inputText;
	private boolean isPassword;

	public SettingTextView(Context context) {
		this(context, null, 0);
	}

	public SettingTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SettingTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		LayoutInflater.from(context).inflate(R.layout.setting_text_view, this);

		nameText = (TextView) findViewById(R.id.textview_setting_name);
		inputText = (EditText) findViewById(R.id.edittext_setting_input);

		TypedArray attrsArray = context.obtainStyledAttributes(attrs, R.styleable.SettingTextView);
		String nameText = attrsArray.getString(R.styleable.SettingTextView_nameText);
		String hintText = attrsArray.getString(R.styleable.SettingTextView_hintText);
		boolean isPassword = attrsArray.getBoolean(R.styleable.SettingTextView_isPassword, false);

		attrsArray.recycle();

		if (!TextUtils.isEmpty(nameText)) {
			setNameText(nameText);
		}

		if (!TextUtils.isEmpty(hintText)) {
			setHintText(hintText);
		}

		this.isPassword = isPassword;
		if (isPassword) {
			setTextPasswordMode();
		}

	}

	public void setNameText(String text) {
		nameText.setText(text);
	}

	public void setHintText(String text) {
		inputText.setHint(text);
	}

	public void setInputText(String text) {
		inputText.setText(text);
	}

	public void setTextPasswordMode() {
		inputText.setInputType(inputText.getInputType() | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		inputText.setSelection(inputText.getText().length());
	}

	public String getInputText() {
		return inputText.getText().toString();
	}

	public boolean isPassword() {
		return isPassword;
	}

}
