package com.android.example.spinner.test;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.KeyEvent;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.android.example.spinner.SpinnerActivity;

public class SpinnerActivityTest extends
		ActivityInstrumentationTestCase2<SpinnerActivity> {

	public static final int ADAPTER_COUNT = 9;
	public static final int INITIAL_POSITION = 0;
	public static final int TEST_POSITION = 5;

	public static final int TEST_STATE_DESTROY_POSITION = 2;
	public static final String TEST_STATE_DESTROY_SELECTION = "Earth";

	public static final int TEST_STATE_PAUSE_POSITION = 4;
	public static final String TEST_STATE_PAUSE_SELECTION = "Jupiter";

	private SpinnerActivity mActivity;
	private Spinner mSpinner;
	private SpinnerAdapter mPlanetData;

	private String mSelection;
	private int mPos;

	public SpinnerActivityTest() {
		super("com.android.example.spinner", SpinnerActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// This turns off touch mode in the device or emulator. If any of your
		// test methods send key events to the application, you must turn off
		// touch mode before you start any activities; otherwise, the call is
		// ignored.
		setActivityInitialTouchMode(false);

		// Stores references to system objects.
		mActivity = getActivity();
		mSpinner = (Spinner) mActivity
				.findViewById(com.android.example.spinner.R.id.Spinner01);
		mPlanetData = mSpinner.getAdapter();
	}

	// The initial conditions test verifies that the application under test is
	// initialized correctly.
	public void testPreConditions() {

		// The item select listener is initialized. This listener is called when
		// a selection is made from the spinner.
		assertTrue(mSpinner.getOnItemSelectedListener() != null);

		// The adapter that provides values to the spinner is initialized.
		assertTrue(mPlanetData != null);

		// The adapter contains the right number of entries.
		assertEquals(mPlanetData.getCount(), ADAPTER_COUNT);
	}

	// The last part of the test compares the selection made by sending the key
	// events to a pre-determined value. This tests that the spinner is working
	// as intended.
	public void testSpinnerUI() {

		// Get focus and set selection. Request focus for the spinner and set
		// its
		// position to default or initial position, "Earth".
		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				mSpinner.requestFocus();
				mSpinner.setSelection(INITIAL_POSITION);
			}
		});

		// Make a selection. Send key events to the spinner to select one of the
		// items.
		this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
		for (int i = 1; i <= TEST_POSITION; i++) {
			this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
		}
		this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);

		// Check the result. Query the current state of the spinner, and compare
		// its current selection to the expected value.
		mPos = mSpinner.getSelectedItemPosition();
		mSelection = (String) mSpinner.getItemAtPosition(mPos);
		TextView resultView = (TextView) mActivity
				.findViewById(com.android.example.spinner.R.id.SpinnerResult);
		String resultText = (String) resultView.getText();
		assertEquals(resultText, mSelection);
	}

	// verifies that the spinner selection is maintained after the entire
	// application is shut down and then restarted
	public void testStateDestroy() {

		// set the spinner selection to a test value
		mActivity.setSpinnerPosition(TEST_STATE_DESTROY_POSITION);
		mActivity.setSpinnerSelection(TEST_STATE_DESTROY_SELECTION);

		// Terminate the activity and restart it
		mActivity.finish();
		mActivity = this.getActivity();

		// Get the current spinner settings from the activity
		int currentPosition = mActivity.getSpinnerPosition();
		String currentSelection = mActivity.getSpinnerSelection();

		// Test the current settings against the test values
		assertEquals(TEST_STATE_DESTROY_POSITION, currentPosition);
		assertEquals(TEST_STATE_DESTROY_SELECTION, currentSelection);
	}

	// verifies that the spinner selection is maintained after the activity is
	// paused and then resumed
	@UiThreadTest
	public void testStatePause() {

		// Get the instrumentation object that is controlling the application
		// under test.
		Instrumentation mInstr = this.getInstrumentation();

		// Set the spinner selection to a test value:
		mActivity.setSpinnerPosition(TEST_STATE_PAUSE_POSITION);
		mActivity.setSpinnerSelection(TEST_STATE_PAUSE_SELECTION);

		// Use instrumentation to call the Activity's onPause():
		mInstr.callActivityOnPause(mActivity);

		// Force the spinner to a different selection:
		mActivity.setSpinnerPosition(0);
		mActivity.setSpinnerSelection("");

		// Use instrumentation to call the Activity's onResume():
		mInstr.callActivityOnResume(mActivity);

		// Get the current state of the spinner:
		int currentPosition = mActivity.getSpinnerPosition();
		String currentSelection = mActivity.getSpinnerSelection();

		// Test the current spinner state against the test values:
		assertEquals(TEST_STATE_PAUSE_POSITION, currentPosition);
		assertEquals(TEST_STATE_PAUSE_SELECTION, currentSelection);
	}

}
