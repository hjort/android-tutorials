package com.android.maps;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

@SuppressWarnings("rawtypes")
public class HelloItemizedOverlay extends ItemizedOverlay {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

	private Context mContext;

	// The constructor must define the default marker for each of the OverlayItems.
	public HelloItemizedOverlay(Drawable defaultMarker) {
		// center-point at the bottom of the image to be the point at which it's attached to the map coordinates
		super(boundCenterBottom(defaultMarker));
	}

	// set up the ability to handle touch events on the overlay items
	public HelloItemizedOverlay(Drawable defaultMarker, Context context) {
		this(defaultMarker);
		mContext = context;
	}

	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	// This uses the member android.content.Context to create a new AlertDialog.Builder and
	// uses the tapped OverlayItem's title and snippet for the dialog's title and message text.
	@Override
	protected boolean onTap(int index) {
		OverlayItem item = mOverlays.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}

}
