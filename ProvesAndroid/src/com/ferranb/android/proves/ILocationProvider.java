package com.ferranb.android.proves;

import android.view.View;

public interface ILocationProvider {
   String getLocationProviderName();
   
   View getLocationProviderView();
   
   boolean isLocationProviderUsable();  
   
   void setLocationProviderUsable(boolean b);  
   
}