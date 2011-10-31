package com.ferranb.android.packageinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ferranb.android.proves.R;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;


public class PermissionsDetails implements IPackageDetails {

	@Override
	public String getMenuString() {
		return "GET_PERMISSIONS";
	}

	@Override
	public int getPackageInfoFlag() {
		return PackageManager.GET_PERMISSIONS;
	}

	final String TITLE = "TITLE";
	final String DETAIL = "DETAIL";
	final String EDIT_TEXT = "ET";
		
	@Override 
	public void pupulateList(Context context, List<Map<String, String>> children, PackageInfo packageInfo)
	{
		if (packageInfo.permissions == null)
			return;
		for (PermissionInfo permission : packageInfo.permissions) {
            
			Map<String, String> curChildMap = new HashMap<String, String>();
            children.add(curChildMap);
            curChildMap.put(TITLE, permission.name);
            
            curChildMap.put(DETAIL, permission.group); 
           
            
            
            curChildMap.put(EDIT_TEXT, permission.nonLocalizedLabel+ "---"+ permission.nonLocalizedDescription);
            
        }
	}
	   
	
	@Override
	public int getLayout() {
		return R.layout.provider_info;
	}

	final String[] KEYS = new String[] { TITLE, DETAIL, EDIT_TEXT};
	
	@Override
	public String[] getKeys() {
		return KEYS;
	}

	final int[] KEY_VIEWS = new int[] { R.id.provider_name, R.id.detall, R.id.editText};
	
	@Override
	public int[] getKeyViews() {
		return KEY_VIEWS;
	}	
}
