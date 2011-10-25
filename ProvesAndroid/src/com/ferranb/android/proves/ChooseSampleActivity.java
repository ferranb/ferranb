package com.ferranb.android.proves;

import com.ferranb.android.utils.SelectActivity;

import android.widget.AdapterView.OnItemClickListener;

public class ChooseSampleActivity extends SelectActivity implements OnItemClickListener {
	
	@Override
	protected SelectActivity.ActivityListItem[] getActivityListItems() {
		return new SelectActivity.ActivityListItem[] {
		  new SelectActivity.ActivityListItem("Location", LocationActivity.class)
		, new SelectActivity.ActivityListItem("Content Providers", ContentProviderListActivity.class)
		};
	}
	
}
