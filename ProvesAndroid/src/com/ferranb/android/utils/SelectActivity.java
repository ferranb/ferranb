package com.ferranb.android.utils;

import com.ferranb.android.proves.R;
import com.ferranb.android.proves.R.layout;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

abstract public class SelectActivity  extends ListActivity implements OnItemClickListener{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		  setListAdapter(new ArrayAdapter<ActivityListItem>(this, R.layout.list_item, getActivityListItems()));

		  ListView lv = getListView();
		  lv.setTextFilterEnabled(true);
		  lv.setOnItemClickListener(this);
	}

	protected static class ActivityListItem {

		String mActivityName;
		Class<?> mActivityClass;
		
		public ActivityListItem(String activityName, Class<?> activityClass)
		{
			mActivityName = activityName;
			mActivityClass = activityClass;
		}
		
		@Override
		public String toString() {
			return mActivityName;
		}
		
		public Class<?> getActivityClass()
		{
			return mActivityClass;
		}
		
	}
	
	abstract protected ActivityListItem[] getActivityListItems();
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		ActivityListItem ali = (ActivityListItem)arg0.getItemAtPosition(position);
		
		Intent intent = new Intent(this, ali.getActivityClass());
		try {
			startActivity(intent);
		} catch (Exception e)
		{
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
		}
	}
	
}
