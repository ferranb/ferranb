package com.ferranb.android.packageinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ferranb.android.proves.R;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.net.Uri;


public class ServicesDetails implements IPackageDetails {

	@Override
	public String getMenuString() {
		return "GET_SERVICES";
	}

	@Override
	public int getPackageInfoFlag() {
		return PackageManager.GET_SERVICES;
	}

	final String TITLE = "TITLE";
	final String DETAIL = "DETAIL";
	final String PERMISSION ="PERM";
	
	@Override 
	public void pupulateList(Context context, List<Map<String, String>> children, PackageInfo packageInfo)
	{
		if (packageInfo.services == null)
			return;
		
		for (ServiceInfo service : packageInfo.services) {
                    	
        	Map<String, String> curChildMap = new HashMap<String, String>();
            children.add(curChildMap);
            curChildMap.put(TITLE, service.name);
            if (service.permission == null)
            	curChildMap.put(PERMISSION, "No  permission");
            else
            	curChildMap.put(PERMISSION, service.permission);

        }
	}
	   

	@Override
	public int getLayout() {
		return R.layout.provider_info;
	}

	final String[] KEYS = new String[] { TITLE, PERMISSION };
	
	@Override
	public String[] getKeys() {
		return KEYS;
	}

	final int[] KEY_VIEWS = new int[] { R.id.provider_name, R.id.read_permission};
	
	@Override
	public int[] getKeyViews() {
		return KEY_VIEWS;
	}

}
