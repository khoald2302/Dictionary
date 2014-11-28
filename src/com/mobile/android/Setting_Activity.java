package com.mobile.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class Setting_Activity extends Activity {

	String[] title;
	String[] url;
	String[] size;
	static List<Item_setting> rowItems = new ArrayList<Item_setting>();
	static ListView mylistview;
	static CustomAdapter_setting adapter = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_layout);
		rowItems = new ArrayList<Item_setting>();
		title = getResources().getStringArray(R.array.Title);

		url = getResources().getStringArray(R.array.URl_LINK);

		size = getResources().getStringArray(R.array.Size);
		for (int i = 0; i < title.length; i++) {
			Item_setting item = new Item_setting(title[i], url[i], size[i]);
			rowItems.add(item);
		}
		mylistview = (ListView) findViewById(R.id.list_setting);
//		adapter = new CustomAdapter_setting(Setting_Activity.this, rowItems);
//		mylistview.setAdapter(adapter);
	}
}