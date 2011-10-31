package com.ferranb.android.packageinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ferranb.android.proves.R;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;


public class ReceiversDetails extends ActivitiesDetails {

	@Override
	public String getMenuString() {
		return "GET_RECEIVERS";
	}

	@Override
	public int getPackageInfoFlag() {
		return PackageManager.GET_RECEIVERS;
	}

	final String TITLE = "TITLE";
	final String DETAIL = "DETAIL";

		
	@Override 
	public void pupulateList(Context context, List<Map<String, String>> children, PackageInfo packageInfo)
	{
		if (packageInfo.receivers == null)
			return;
		
		for (ActivityInfo activity : packageInfo.receivers) {
            
        	
        	Map<String, String> curChildMap = new HashMap<String, String>();
            children.add(curChildMap);
            curChildMap.put(TITLE, activity.name);
            
            curChildMap.put(DETAIL, activity.targetActivity);
               
        }
	}
	  

}
