package com.android.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SongsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TextView textview = new TextView(this);
		textview.setText("This is the Songs tab");
		setContentView(textview);
	}

}
