package com.ferranb.android.packageinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;

import com.ferranb.android.proves.R;

public class PackageInfoActivity  extends Activity implements OnItemSelectedListener {
	
	ExpandableListAdapter mExpandableListAdapter;
	

	ExpandableListView mExpandableListView;
	Spinner mSpinner;
	
	IPackageDetails[] mPackageDetails;
	
	public PackageInfoActivity()
	{
		mPackageDetails = new IPackageDetails[] {
			  new ProviderDetails()
			 ,new ServicesDetails()
			 ,new ReceiversDetails()
			 ,new ActivitiesDetails()
			 ,new PermissionsDetails()
		};
	}

	final String PKG_TITLE = "PKG_TITLE";
	final String PKG_DETAIL = "PKG_DETAIL";	
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.package_info);
        
        mExpandableListView = (ExpandableListView) findViewById(R.id.expandable_listview);
                
        mSpinner = (Spinner) findViewById(R.id.package_spinner);    
        mSpinner.setOnItemSelectedListener(this);
	}     

	
	void fillExpandableListView(IPackageDetails details)
	{
		List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
		List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
	    int flag = details.getPackageInfoFlag();  
	    for (PackageInfo pack : getPackageManager().getInstalledPackages(flag)) {
        	Map<String, String> curGroupMap = new HashMap<String, String>();
            groupData.add(curGroupMap);
            curGroupMap.put(PKG_TITLE, pack.packageName);
            curGroupMap.put(PKG_DETAIL, pack.versionName);
            List<Map<String, String>> children = new ArrayList<Map<String, String>>();
            details.pupulateList(this, children, pack);
	        childData.add(children);
	    }
	
        // Set up our adapter
        mExpandableListAdapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] { PKG_TITLE, PKG_DETAIL },
                new int[] { android.R.id.text1, android.R.id.text2 },
                childData,
                details.getLayout(),
                details.getKeys(),
                details.getKeyViews()
                );
        
		mExpandableListView.setVisibility(View.VISIBLE);		
		mExpandableListView.setAdapter(mExpandableListAdapter);	
        mExpandableListView.invalidate();	        
	}

	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		String value = parent.getItemAtPosition(pos).toString();
		boolean found = false;
		for (IPackageDetails p : mPackageDetails)
		{
			String menuString = p.getMenuString();
			if (value.equals(menuString)) {
				fillExpandableListView(p);
				found = true;
				break;
			}
		}
		if (!found)
		{
			Log.e("","Not implemented: "+value);
		}
		/*else if (value.equals("GET_GIDS"))
			flag = PackageManager.GET_GIDS;
		else if (value.equals("GET_CONFIGURATIONS"))
			flag = PackageManager.GET_CONFIGURATIONS;
		else if (value.equals("GET_INSTRUMENTATION"))
			flag = PackageManager.GET_INSTRUMENTATION;
		else if (value.equals("GET_SIGNATURES"))
			flag = PackageManager.GET_SIGNATURES;
		else if (value.equals("GET_UNINSTALLED_PACKAGES"))
			flag = PackageManager.GET_UNINSTALLED_PACKAGES;
		else
			throw new RuntimeException("Not implemented");*/
	
	
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		mExpandableListView.setVisibility(View.INVISIBLE);		
	}



}
