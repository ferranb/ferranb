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


public class ActivitiesDetails implements IPackageDetails {

	@Override
	public String getMenuString() {
		return "GET_ACTIVITIES";
	}

	@Override
	public int getPackageInfoFlag() {
		return PackageManager.GET_ACTIVITIES;
	}

	final String TITLE = "TITLE";
	final String DETAIL = "DETAIL";
		
	@Override 
	public void pupulateList(Context context, List<Map<String, String>> children, PackageInfo packageInfo)
	{
		if (packageInfo.activities == null)
			return;
		for (ActivityInfo activity : packageInfo.activities) {
            
        	
        	Map<String, String> curChildMap = new HashMap<String, String>();
            children.add(curChildMap);
            curChildMap.put(TITLE, activity.name);
            
            curChildMap.put(DETAIL, activity.targetActivity);
               
        }
	}
	  

	@Override
	public int getLayout() {
		return R.layout.provider_info;
	}

	final String[] KEYS = new String[] { TITLE, DETAIL};
	
	@Override
	public String[] getKeys() {
		return KEYS;
	}

	final int[] KEY_VIEWS = new int[] { R.id.provider_name, R.id.detall};
	
	@Override
	public int[] getKeyViews() {
		return KEY_VIEWS;
	}	
}
