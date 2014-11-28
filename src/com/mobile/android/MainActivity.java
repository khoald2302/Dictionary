package com.mobile.android;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class MainActivity extends TabActivity {
	private MenuItem item;
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	private ProgressDialog progressDialog;
	ArrayList<Contact> listData = null;
	int id = 0;
	List<Item_setting> rowItems;
	ListView mylistview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (isOnline() == false) {
			Toast.makeText(MainActivity.this, "Vui long kiem tra ket noi",
					Toast.LENGTH_LONG).show();
		}
		// Tra tá»«
		final TabHost tabHost = getTabHost();

		TabSpec tratuSpec = tabHost.newTabSpec("Tra Tu");

		tratuSpec.setIndicator("",
				getResources().getDrawable(R.drawable.ic_action_search));
		Intent tratuIntent = new Intent(this, TraTu_Activity.class);
		// Tab Content
		tratuSpec.setContent(tratuIntent);

		// Tu vá»±ng
		TabSpec tuvungSpec = tabHost.newTabSpec("Tu Vung");
		tuvungSpec.setIndicator("",
				getResources().getDrawable(R.drawable.ic_action_labels));
		Intent tuvungIntent = new Intent(this, TuVung_Activity.class);
		tuvungSpec.setContent(tuvungIntent);

		// yeu thich
		TabSpec yeuthichSpec = tabHost.newTabSpec("Yeu Thich");
		yeuthichSpec.setIndicator("",
				getResources().getDrawable(R.drawable.ic_action_favorite));
		Intent yeuthichIntent = new Intent(this, YeuThich_Activity.class);
		yeuthichSpec.setContent(yeuthichIntent);
		// Setting
		TabSpec settingSpec = tabHost.newTabSpec("Setting");
		settingSpec.setIndicator("",
				getResources().getDrawable(R.drawable.action_settings));
		Intent settingIntent = new Intent(this, Setting_Activity.class);
		settingSpec.setContent(settingIntent);

		tabHost.addTab(tratuSpec); // Adding tratu tab
		// tabHost.addTab(tuvungSpec); // Adding tuvung tab
		tabHost.addTab(yeuthichSpec); // Adding yeuthich tab
		tabHost.addTab(settingSpec);// adding setting tab
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				switch (tabHost.getCurrentTab()) {
				case 0:
					// do what you want when tab 0 is selected
					DatabaseHandler db1 = new DatabaseHandler(MainActivity.this);
					List<Contact> contacts = db1.getAllContacts();
					for (Contact cn : contacts) {
						Contact contact = new Contact();
						// Log.i(null, "n:" + cn.getId());
						// Log.i(null, "id:" + cn.get_title());
						contact.setId(cn.getId());
						contact.set_title(cn.get_title());
						contact.set_like(cn.get_like());
						contact.set_propeties(cn.get_propeties());
						TraTu_Activity.arrayList.add(contact);
					}
					TraTu_Activity.adap = new class_Custom_Tratuvung(
							MainActivity.this, TraTu_Activity.arrayList,
							R.layout.custom_yeuthich);
					TraTu_Activity.list.setAdapter(TraTu_Activity.adap);
					TraTu_Activity.adap.notifyDataSetChanged();
					break;

					case 1: Toast.makeText(MainActivity.this, "Main",
							Toast.LENGTH_SHORT).show();
					DatabaseHandler db = new DatabaseHandler(MainActivity.this);

					YeuThich_Activity.arrayList = db.findByStatus(1);
					Log.i(null, "size:" + YeuThich_Activity.arrayList.size());
					YeuThich_Activity.yeuthich_Adapter = new Yeuthich_Adapter(
							MainActivity.this, YeuThich_Activity.arrayList);
					YeuThich_Activity.lv
							.setAdapter(YeuThich_Activity.yeuthich_Adapter);
					YeuThich_Activity.yeuthich_Adapter.notifyDataSetChanged();
					break;
				case 2:
					Setting_Activity.adapter = new CustomAdapter_setting(
							MainActivity.this, Setting_Activity.rowItems);
					Setting_Activity.mylistview
							.setAdapter(Setting_Activity.adapter);
					Setting_Activity.adapter.notifyDataSetChanged();
					break;
				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.down, menu);
		// inflater.inflate(R.menu.spiner, menu);
		// // item = menu.findItem(R.id.menu_spin);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.save:
			// https://docs.google.com/uc?authuser=0&id=0B7yvSvRAR5IEclFWZFd6TVV3MnM&export=download
			if (isOnline() == false) {
				Toast.makeText(MainActivity.this, "Vui long kiem tra ket noi",
						Toast.LENGTH_LONG).show();
			} else if (isOnline() == true) {
				String url = "https://docs.google.com/uc?export=download&id=0B7yvSvRAR5IEWDE1NXRBSWdYS1E"; // "https://docs.google.com/uc?export=download&id=0B7yvSvRAR5IEWDE1NXRBSWdYS1E";
				new DownloadFileAsync().execute(url);
			}
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_DOWNLOAD_PROGRESS:
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setMessage("Downloading file...");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setCancelable(false);
			progressDialog.show();
			return progressDialog;
		default:
			return null;
		}
	}

	class DownloadFileAsync extends AsyncTask<String, String, String> {
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			int count;
			try {//
				URL url = new URL(params[0]);
				URLConnection conexion = url.openConnection();
				conexion.connect();
				int lengthofFile = conexion.getContentLength();
				// Log.d("ANDRO_ASYNC", "Length of file: " + lengthofFile);
				InputStream input = new BufferedInputStream(url.openStream());
				parseData(input);
				// OutputStream output = new FileOutputStream(
				// "/data/data/com.example.apptudien/files/file.dict");

				byte data[] = new byte[1024];
				long total = 0;
				while ((count = input.read(data)) != -1) {
					total += count;
					publishProgress("" + (int) ((total * 100) / lengthofFile));
					// output.write(data, 0, count);
				}
				// output.flush();
				// output.close();
				input.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub

			Toast.makeText(MainActivity.this, "Ä�Ã£ Down HoÃ n Táº¥t",
					Toast.LENGTH_SHORT).show();

			// parseData();
			// Log.e(null, "23:" + listData.size());
			DatabaseHandler db = new DatabaseHandler(MainActivity.this);
			// Log.i(null, "dá»­ liá»‡u");
			for (int i = 0; i < listData.size(); i++) {
				Contact contact = new Contact();
				// Log.i(null, "dá»­ liá»‡u");
				// db.addContact(new Contact(listData.get(i).get_title(),
				// listData
				// .get(i).get_propeties()));
				String str1 = String.valueOf(i);
				// Log.i(null, "str1:" + str1);
				// Log.i(null, "pháº§n tá»­:" + i);
				db.addContact(new Contact(str1, listData.get(i).get_title(),
						listData.get(i).get_propeties(), "0"));
				// Log.i(null, "dá»­ liá»‡u");
				String str = listData.get(i).get_title();
			}
			DatabaseHandler db1 = new DatabaseHandler(MainActivity.this);
			List<Contact> contacts = db1.getAllContacts();
			for (Contact cn : contacts) {
				Contact contact = new Contact();
				// Log.i(null, "n:" + cn.getId());
				// Log.i(null, "id:" + cn.get_title());
				contact.setId(cn.getId());
				contact.set_title(cn.get_title());
				contact.set_like(cn.get_like());
				contact.set_propeties(cn.get_propeties());
				TraTu_Activity.arrayList.add(contact);
			}
			TraTu_Activity.adap = new class_Custom_Tratuvung(MainActivity.this,
					TraTu_Activity.arrayList, R.layout.custom_yeuthich);
			TraTu_Activity.list.setAdapter(TraTu_Activity.adap);
			TraTu_Activity.adap.notifyDataSetChanged();
			// Log.i(null, "33");

			dismissDialog(DIALOG_DOWNLOAD_PROGRESS);

		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			showDialog(DIALOG_DOWNLOAD_PROGRESS);

		}

		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			// Log.d("ANDRO_ASYNC", values[0]);
			progressDialog.setProgress(Integer.parseInt(values[0]));
		}
	}

	public ArrayList<Contact> parseData(InputStream input) {
		StringBuilder sb = new StringBuilder();
		Contact data = new Contact();
		listData = new ArrayList<Contact>();
		try {
			// FileInputStream fileinputstream = openFileInput("file.dict");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			String mLine = reader.readLine();
			int i = 0;
			while (mLine != null) {
				// process line!
				if (mLine.length() > 1
						&& (mLine.indexOf("@") == 0 || mLine.indexOf("@") == 1)) {
					if (String.valueOf(sb) == null
							|| !String.valueOf(sb).equals("")) {
						// data.setContent(String.valueOf(sb)) ;
						data.set_propeties(String.valueOf(sb));
						listData.add(data);
						data = new Contact();
						sb = new StringBuilder();
					}
					data.set_title(mLine.split("@")[1]);

				} else {
					sb.append(mLine);
					sb.append("\n");
				}
				mLine = reader.readLine();
			}

			reader.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return listData;
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}

}
