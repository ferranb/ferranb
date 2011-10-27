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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;

public class PackageInfoActivity  extends Activity implements OnItemSelectedListener {
	
	ExpandableListAdapter mAdapter;
	

	ExpandableListView mExpandableListView;
	Spinner mSpinner;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.package_info);
        
        mExpandableListView = (ExpandableListView) findViewById(R.id.expandable_listview);
        
        mSpinner = (Spinner) findViewById(R.id.package_spinner);        
        mSpinner.setOnItemSelectedListener(this);
        
   
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

	
	final String TITLE = "T";
	final String DETALL = "D";	
	final String READ_PERM = "R";
	final String WRITE_PERM = "W";
	
	private void fillExpandableListView(int flag)
	{
        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
        for (PackageInfo pack : getPackageManager().getInstalledPackages(flag)) {
        	if (pack.providers == null)
        		continue;
        	Map<String, String> curGroupMap = new HashMap<String, String>();
            groupData.add(curGroupMap);
            curGroupMap.put(TITLE, pack.packageName);
            curGroupMap.put(DETALL, Integer.toString(flag));
            
	        List<Map<String, String>> children = new ArrayList<Map<String, String>>();
	        for (ProviderInfo provider : pack.providers) {
                Map<String, String> curChildMap = new HashMap<String, String>();
                children.add(curChildMap);
                curChildMap.put(TITLE, provider.name);
                curChildMap.put(DETALL, "content://"+provider.authority);
                if (provider.readPermission != null)
                	curChildMap.put(READ_PERM, provider.readPermission);
                else
                	curChildMap.put(READ_PERM, "No read permission");
                if (provider.writePermission != null)
                    curChildMap.put(WRITE_PERM, provider.writePermission);
                else
                	curChildMap.put(READ_PERM, "No write permission");
                
	        }
	        childData.add(children);
	    }

       
        // Set up our adapter
        mAdapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] { TITLE, DETALL },
                new int[] { android.R.id.text1, android.R.id.text2 },
                childData,
                R.layout.provider_info,
                new String[] { TITLE, DETALL, READ_PERM, WRITE_PERM },
                new int[] { R.id.provider_name, R.id.detall, R.id.read_permission, R.id.write_permission }
                );
        mExpandableListView.setAdapter(mAdapter);	
        mExpandableListView.invalidate();
		
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		String value = parent.getItemAtPosition(pos).toString();
		int flag;
		if (value.equals("GET_ACTIVITIES"))
			flag = PackageManager.GET_ACTIVITIES;
		else if (value.equals("GET_GIDS"))
			flag = PackageManager.GET_GIDS;
		else if (value.equals("GET_CONFIGURATIONS"))
			flag = PackageManager.GET_CONFIGURATIONS;
		else if (value.equals("GET_INSTRUMENTATION"))
			flag = PackageManager.GET_INSTRUMENTATION;
		else if (value.equals("GET_PERMISSIONS"))
			flag = PackageManager.GET_PERMISSIONS;
		else if (value.equals("GET_PROVIDERS"))
			flag = PackageManager.GET_PROVIDERS;
		else if (value.equals("GET_RECEIVERS"))
			flag = PackageManager.GET_RECEIVERS;
		else if (value.equals("GET_SERVICES"))
			flag = PackageManager.GET_SERVICES;
		else if (value.equals("GET_SIGNATURES"))
			flag = PackageManager.GET_SIGNATURES;
		else if (value.equals("GET_UNINSTALLED_PACKAGES"))
			flag = PackageManager.GET_UNINSTALLED_PACKAGES;
		else
			throw new RuntimeException("Not implemented");
	
		mExpandableListView.setVisibility(View.VISIBLE);		
		fillExpandableListView(flag);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		mExpandableListView.setVisibility(View.INVISIBLE);		
	}
	
	
	
	
}
