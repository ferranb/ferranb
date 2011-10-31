package com.ferranb.android.packageinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ferranb.android.proves.R;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;


public class ProviderDetails implements IPackageDetails {

	@Override
	public String getMenuString() {
		return "GET_PROVIDERS";
	}

	@Override
	public int getPackageInfoFlag() {
		return PackageManager.GET_PROVIDERS;
	}

	final String TITLE = "TITLE";
	final String DETAIL = "DETAIL";
	final String READ_PERM ="RP";
	final String WRITE_PERM = "WP";       
	final String EDIT_TEXT = "ET";
		
	@Override 
	public void pupulateList(Context context, List<Map<String, String>> children, PackageInfo packageInfo)
	{
		if (packageInfo.providers == null)
			return;
		for (ProviderInfo provider : packageInfo.providers) {
            
        	
        	Map<String, String> curChildMap = new HashMap<String, String>();
            children.add(curChildMap);
            curChildMap.put(TITLE, provider.name);
            
            Uri uri = new Uri.Builder().scheme("content").authority(provider.authority).build();
            curChildMap.put(DETAIL, uri.toString());                
            if (provider.readPermission == null)
            	curChildMap.put(READ_PERM, "No read permission");
            else
            	curChildMap.put(READ_PERM, provider.readPermission);
            if (provider.writePermission == null)
            	curChildMap.put(READ_PERM, "No write permission");
            else
                curChildMap.put(WRITE_PERM, provider.writePermission);
            
            //String data = getContentProviderString(context.getContentResolver(), uri);
            curChildMap.put(EDIT_TEXT, "data");
            
        }
	}
	   
	
	
	private String getContentProviderString(ContentResolver contentResolver, Uri uri)
    {
		
    	StringBuffer str = new StringBuffer();
    	str.append(uri.toString());
    
    	Cursor cur = null;
         
        try{
         	cur = contentResolver.query(uri, null, null, null, null);
         
	 		for (int i = 0; i < cur.getColumnCount(); i++)
	 		{
	 			str.append(", ");
	 			str.append(cur.getColumnNames()[i]);
	 		}
	 		str.append(" -- ");
	 		if (cur.moveToFirst())
	 		{	
	 			do {
		 			for (int i = 0; i < cur.getColumnCount(); i++)
		 			{
		 		 		Object o = cur.getString(i);
		 				if (o == null)
		 					o = "(null)";
		 			}
		 	    } while (cur.moveToNext());
		 	}
         } catch(Exception e)
         {
         	System.out.print(e.getMessage());
         	str.append(" -Error- ");
     	    str.append(e.getMessage());
         } 
        cur.close();

        return str.toString();
    }

	@Override
	public int getLayout() {
		return R.layout.provider_info;
	}

	final String[] KEYS = new String[] { TITLE, DETAIL, READ_PERM, WRITE_PERM , EDIT_TEXT};
	
	@Override
	public String[] getKeys() {
		return KEYS;
	}

	final int[] KEY_VIEWS = new int[] { R.id.provider_name, R.id.detall, R.id.read_permission, R.id.write_permission, R.id.editText};
	
	@Override
	public int[] getKeyViews() {
		return KEY_VIEWS;
	}	
}
