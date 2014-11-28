package com.mobile.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class YeuThich_Activity extends Activity {
	static ListView lv;
	static ArrayList<Contact> arrayList = new ArrayList<Contact>();
	static Yeuthich_Adapter yeuthich_Adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// getWindow().getDecorView().findViewById(android.R.id.content).invalidate();
		setContentView(R.layout.yeuthich_layout);
		//Toast.makeText(YeuThich_Activity.this,"Tab 2",Toast.LENGTH_SHORT).show();
		lv = (ListView) findViewById(R.id.list_yeuthich);

		arrayList = new ArrayList<Contact>();
		

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				DatabaseHandler db = new DatabaseHandler(YeuThich_Activity.this);
				String title = arrayList.get(position).get_title();
				//Log.i(null, "Phaanf :" + arrayList.get(position).get_title());

				DatabaseHandler db1 = new DatabaseHandler(
						YeuThich_Activity.this);

				Contact contacts = db1.getContact(arrayList.get(position)
						.get_title());

				String propeties = contacts.get_propeties();
				//Log.i(null, "protested:" + contacts.get_propeties());
				Intent intent = new Intent(YeuThich_Activity.this,
						TuVung_Activity.class);
				intent.putExtra("title", title);
				intent.putExtra("pro", propeties);
				startActivity(intent);

			}
		});

	}

}
