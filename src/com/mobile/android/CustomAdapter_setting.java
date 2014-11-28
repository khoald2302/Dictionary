package com.mobile.android;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter_setting extends BaseAdapter {
	Context context;
	List<Item_setting> rowItems;
	private LayoutInflater mInflater;

	CustomAdapter_setting(Context context, List<Item_setting> rowItems) {
		this.context = context;
		this.rowItems = rowItems;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return rowItems.size();
	}

	@Override
	public Item_setting getItem(int position) {
		return rowItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/* private view holder class */
	private class ViewHolder {

		TextView Title;
		TextView Url;
		TextView size;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.custom_setting, null);
			holder.Title = (TextView) convertView
					.findViewById(R.id.txt_title_setting);
			holder.size = (TextView) convertView
					.findViewById(R.id.txt_size_setting);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.Title.setText(rowItems.get(position).getTitle());
		holder.size.setText(rowItems.get(position).getSize());

		holder.Title.setTag(position);

		holder.size.setTag(position);
		return convertView;
	}

}
