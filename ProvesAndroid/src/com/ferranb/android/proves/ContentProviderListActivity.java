package com.ferranb.android.proves;

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

public class ContentProviderListActivity  extends ExpandableListActivity  {
	
	ExpandableListAdapter mExpandableListAdapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_providers);
     	
     // Use the ContentUris method to produce the base URI for the contact with _ID == 23.
      /*  Uri myPerson = ContentUris.withAppendedId(CallLog.Calls.CONTENT_URI, 1);
        myPerson = CallLog.CONTENT_URI;
        
        // myPerson = Uri.withAppendedPath(CallLog.Calls.CONTENT_URI, "");
        myPerson = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
*/
        EditText e = (EditText) this.findViewById(R.id.editText1);
        
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
	    }

	
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
	
	private class ContentProviderExpandableListAdapter extends BaseExpandableListAdapter
	{
		
	}
	
	
	
}
