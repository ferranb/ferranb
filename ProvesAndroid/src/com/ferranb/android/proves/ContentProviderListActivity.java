package com.ferranb.android.proves;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.app.ListActivity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.SimpleExpandableListAdapter;

public class ContentProviderListActivity  extends ExpandableListActivity  {
	
	ExpandableListAdapter mAdapter;
	
	String PROVEIDOR = "Proveidor";
	String DETALL = "Detall";
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
        for (PackageInfo pack : getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS)) {
        	Map<String, String> curGroupMap = new HashMap<String, String>();
            groupData.add(curGroupMap);
            curGroupMap.put(PROVEIDOR, pack.packageName);
            curGroupMap.put(DETALL, pack.toString());
            
            List<Map<String, String>> children = new ArrayList<Map<String, String>>();
            
        	ProviderInfo[] providers = pack.providers;
	        if (providers != null) {
	            for (ProviderInfo provider : providers) {
	                Map<String, String> curChildMap = new HashMap<String, String>();
	                children.add(curChildMap);
	                curChildMap.put(PROVEIDOR, provider.name);
	                curChildMap.put(DETALL, provider.toString());
	               }
	            childData.add(children);
	        }
	    }

       
        // Set up our adapter
        mAdapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                new String[] { PROVEIDOR, DETALL },
                new int[] { android.R.id.text1, android.R.id.text2 },
                childData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] { PROVEIDOR, DETALL },
                new int[] { android.R.id.text1, android.R.id.text2 }
                );
        setListAdapter(mAdapter);
       /*     
        for (PackageInfo pack : getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS)) {
	        ProviderInfo[] providers = pack.providers;
	        if (providers != null) {
	            for (ProviderInfo provider : providers) {
	            	e.append(provider.name);
	            	if(provider.readPermission != null)
	            		e.append(provider.readPermission);
	            	if(provider.writePermission != null)
		            	e.append(provider.writePermission);
	                e.append(provider.toString());
	                e.append("--------");
	                //Log.d("Example", "provider: " + provider.authority);
	            }
	        }
	    }*/

	
	}     

	private void getContentProviderList()
	{
		 for (PackageInfo pack : getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS)) {
		        ProviderInfo[] providers = pack.providers;
		        if (providers != null) {
		            for (ProviderInfo provider : providers) {
		                //Log.d("Example", "provider: " + provider.authority);
		            }
		        }
		    }

	}
	
	private String getContentProviderString(Uri uri)
    {
    	StringBuffer str = new StringBuffer();
    	str.append(uri.toString());
    
    	Cursor cur = null;
         
        try{
         	// Then query for this specific record:
         	cur = managedQuery(uri, null, null, null, null);
         
	 		for (int i = 0; i < cur.getColumnCount(); i++)
	 		{
	 			str.append(", ");
	 			str.append(cur.getColumnNames()[i]);
	 		}
	 		str.append(" -- ");
	 		cur.moveToFirst();
	 		do {
	 			for (int i = 0; i < cur.getColumnCount(); i++)
	 			{
	 				//editText.append(",");
	 				Object o = cur.getString(i);
	 				if (o == null)
	 					o = "(null)";
	 				//editText.append((String)o);
	 			}
	 	    } while (cur.moveToNext());
         } catch(Exception e)
         {
         	System.out.print(e.getMessage());
         	str.append(" -- ");
     	    str.append(e.getMessage());
         } 

        return str.toString();
    }
	
	
	
	
}
