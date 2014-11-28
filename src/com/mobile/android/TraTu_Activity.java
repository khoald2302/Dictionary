package com.mobile.android;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TraTu_Activity extends Activity {

	ImageView button;
	TextView tv1;
	EditText edtSerach;
	// static AutoCompleteTextView edt;
	static ListView list;
	static ArrayList<Contact> arrayList = null;
	static class_Custom_Tratuvung adap = null;
	static int count;
	DatabaseHandler db;
	private View mFooterView;
	int mult = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tratu_layout);
		edtSerach = (EditText) findViewById(R.id.edit_search);
		// mHandler = new Handler();
		search();
		list = (ListView) findViewById(R.id.list);

		arrayList = new ArrayList<Contact>();
		adap = new class_Custom_Tratuvung(TraTu_Activity.this, arrayList,
				R.layout.custom_yeuthich);
		db = new DatabaseHandler(TraTu_Activity.this);

		button = (ImageView) findViewById(R.id.imgSearch);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				edtSerach.setText("");
			}
		});

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				DatabaseHandler db = new DatabaseHandler(TraTu_Activity.this);
				String title = arrayList.get(position).get_title();
				//Log.i(null, "Phaanf :" + arrayList.get(position).get_title());

				DatabaseHandler db1 = new DatabaseHandler(TraTu_Activity.this);

				Contact contacts = db1.getContact(arrayList.get(position)
						.get_title());

				String propeties = contacts.get_propeties();
				//Log.i(null, "protested:" + contacts.get_propeties());
				Intent intent = new Intent(TraTu_Activity.this,
						TuVung_Activity.class);
				String id_tuvung = String.valueOf(position);
				intent.putExtra("title", title);
				intent.putExtra("pro", propeties);
				intent.putExtra("id", id_tuvung);
				startActivity(intent);

			}
		});
	}

	private void search() {
		edtSerach.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String text = edtSerach.getText().toString()
						.toLowerCase(Locale.getDefault());
				adap.filter(text);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// edtSerach.setText("");

			}
		});
	}

}