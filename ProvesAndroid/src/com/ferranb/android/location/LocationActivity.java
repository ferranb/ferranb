package com.ferranb.android.location;

import com.ferranb.android.proves.R;
import com.ferranb.android.proves.R.id;
import com.ferranb.android.proves.R.layout;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

public class LocationActivity extends Activity implements OnClickListener{
	
	EditText mEditText;

	ILocationProvider[] mViewLocationProviders;
	
	ToggleButton mGPSButton;
	ToggleButton mNetworkButton;
	ToggleButton mPassiveButton;
	Button mLastLocationButton;
	
	LocationManager mLocationManager;
    LocationListener mLocationListener;
    
    final String LOCATION_MANAGER_PASSIVE = "passive";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mEditText = (EditText) findViewById(R.id.text);
        mLastLocationButton = (Button) findViewById(R.id.LastLocationButton);
        
        mViewLocationProviders = new ILocationProvider[2];
        mViewLocationProviders[0] = new LocationProviderToggleButton(findViewById(R.id.GPSButton), LocationManager.GPS_PROVIDER);
        mViewLocationProviders[1] = new LocationProviderToggleButton(findViewById(R.id.NetworkButton), LocationManager.NETWORK_PROVIDER);
        //mViewLocationProviders[2] = new LocationProviderToggleButton(findViewById(R.id.PassiveButton), LOCATION_MANAGER_PASSIVE);
        
    	mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        manageLocationChange();

    	mLocationListener = new LocationListener() {
    		int updateOrder = 0;
    		
    		@Override
			public void onLocationChanged(Location location) {
    			showLocation(location);
    		}

    	    public void onStatusChanged(String provider, int status, Bundle extras) 
    	    {
    	    	mEditText.setText("onStatusChanged"+" "+provider+" "+extras.toString());
    	    }

    	    public void onProviderEnabled(String provider) 
    	    {
    	    	manageLocationChange();
    	    }

    	    public void onProviderDisabled(String provider) 
    	    {
    	    	manageLocationChange();
    	    }

    	  };        		 
       
    	  
    	this.mLastLocationButton.setOnClickListener(this);

        
        for (ILocationProvider lp : mViewLocationProviders)
        {
        	lp.getLocationProviderView().setOnClickListener(this);
        	
      		mLocationManager.requestLocationUpdates(lp.getLocationProviderName(), 0, 100, mLocationListener);        	
        }
  
            
    }
    
    private void showLocation(Location location)
    {
      // Called when a new location is found by the network location provider.
     //makeUseOfNewLocation(location);
		mEditText.append(" "+location.toString());
		mEditText.append("                 ");
		mEditText.append("-----------------");
		mEditText.append("                 ");
      //mEditText.append(mLocationListenerNet.toString());
	
    }
    
    private void manageLocationChange()
    {
    	for (ILocationProvider lp : mViewLocationProviders)
        {
    		if (lp.getLocationProviderName() == null)
    			continue;
    		boolean enabled;
    		enabled = mLocationManager.isProviderEnabled(lp.getLocationProviderName());
    		if (enabled)
    		{
    			((ToggleButton) lp.getLocationProviderView()).setChecked(enabled);
    		}
        }
        
    }
    
        
	@Override
	public void onClick(View v) 
	{
		for (ILocationProvider lp : mViewLocationProviders)
        {
			if (v == mLastLocationButton)
			{
				if (!mLocationManager.isProviderEnabled(lp.getLocationProviderName()))
					continue;
				Location lastKnownLocation = mLocationManager.getLastKnownLocation(lp.getLocationProviderName());
				showLocation(lastKnownLocation);
				continue;
			}
			if (lp.getLocationProviderView() != v)
    			continue;
    		// Update location provider state
    		ToggleButton tb = (ToggleButton) lp.getLocationProviderView();
    		lp.setLocationProviderUsable(tb.isChecked());
    		
        }
	}
    
	
}