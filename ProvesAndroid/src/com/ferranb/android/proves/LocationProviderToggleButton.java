package com.ferranb.android.proves;

import android.view.View;
import android.widget.ToggleButton;

public class LocationProviderToggleButton implements ILocationProvider {

	ToggleButton mToggleButton;
	String mProviderName;

	public LocationProviderToggleButton(ToggleButton tb, String pn)
	{
		mToggleButton = tb;
		mProviderName = pn;
		
	}
	
	public LocationProviderToggleButton(View tb, String pn)
	{
		this((ToggleButton) tb, pn);
	}
	
	@Override
	public String getLocationProviderName() {
		return mProviderName;
	}

	@Override
	public View getLocationProviderView() {
		return mToggleButton;
	}
	
	@Override
	public boolean isLocationProviderUsable() {
		return mToggleButton.isChecked();
	}

	@Override
	public void setLocationProviderUsable(boolean usable) {
		mToggleButton.setChecked(usable);
	}

}
