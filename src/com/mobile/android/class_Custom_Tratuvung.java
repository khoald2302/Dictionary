package com.mobile.android;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



import android.R.array;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class class_Custom_Tratuvung extends BaseAdapter {

	Context context = null;
	List<Contact> SearchArr = null;
	private LayoutInflater mInflater;
	private ArrayList<Contact> arraylist;
	public String Id;


	int layoutId;

	public class_Custom_Tratuvung(Context context, List<Contact> arr, int lyaout) {
		mInflater = LayoutInflater.from(context);

		this.context = context;

		this.SearchArr = arr;
		this.arraylist = new ArrayList<Contact>();
		this.arraylist.addAll(SearchArr);
	}

	public class_Custom_Tratuvung() {
	}

	public class ViewHolder {
		TextView txtTitle;
		ImageView btnFavo;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arraylist.size();
	}

	@Override
	public Contact getItem(int position) {
		// TODO Auto-generated method stub
		return SearchArr.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.custom_yeuthich, null);
			holder.txtTitle = (TextView) convertView
					.findViewById(R.id.txtcustom);

			holder.btnFavo = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txtTitle.setText(SearchArr.get(position).get_title());
		holder.btnFavo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				DatabaseHandler db = new DatabaseHandler(context);
				// String id = SearchArr.get(position).get_title();
				// Log.e("title", "   " + id);
				// db.UPDATE_Status("1", id);
				// Log.i(null, "get id:" + SearchArr.get(position).getId());
				 String id_tuvung = SearchArr.get(position).getId();
				 Id =id_tuvung;
				db.getlikeupdate(id_tuvung, "1");

			}
		});

		holder.txtTitle.setTag(position);

		holder.btnFavo.setTag(position);

		return convertView;
	}

	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		SearchArr.clear();
		if (charText.length() == 0) {
			SearchArr.addAll(arraylist);
		} else {
			for (Contact wp : arraylist) {
				if (wp.get_title().toLowerCase(Locale.getDefault())
						.contains(charText)) {
					SearchArr.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}
}
