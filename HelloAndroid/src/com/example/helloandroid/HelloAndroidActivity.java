package com.example.helloandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * An Activity is a single application entity that is used to perform actions.
 * An application may have many separate activities, but the user interacts with
 * them one at a time.
 * 
 * @see http://developer.android.com/resources/tutorials/hello-world.html
 */
public class HelloAndroidActivity extends Activity {

	/**
	 * Called when the activity is first created. It is where you should perform
	 * all initialization and UI setup.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("Hello, Android");
		setContentView(tv);

//		Object o = null;
//		o.toString();

		// setContentView(R.layout.main);
	}

}