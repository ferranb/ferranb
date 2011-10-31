package com.ferranb.android.packageinfo;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.pm.PackageInfo;

public interface IPackageDetails {
	
   String getMenuString();
   
   int getPackageInfoFlag();
   
   void pupulateList(Context context, List<Map<String, String>> children, PackageInfo packageInfo);   
   
   int getLayout();
   
   String[] getKeys();
   
   int[] getKeyViews();
   
   
}
