package com.android.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HelloWebView extends Activity {

	WebView mWebView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mWebView = (WebView) findViewById(R.id.webview);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl("http://www.google.com");
		
		// override the WebViewClient class and enable this Activity to handle its own URL requests
		mWebView.setWebViewClient(new HelloWebViewClient());
	}
	
	private class HelloWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        // the method has handled the URL and the event should not propagate
	        return true;
	    }
	}
	
	// handle the BACK button key press
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
	        mWebView.goBack();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
}