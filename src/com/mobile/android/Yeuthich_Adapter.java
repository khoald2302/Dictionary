package com.mobile.android;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Yeuthich_Adapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private List<Contact> searchList = null;
	private ArrayList<Contact> arrlist;

	public Yeuthich_Adapter(Context context, ArrayList<Contact> searchList) {
		this.mContext = context;
		this.searchList = searchList;

		mInflater = LayoutInflater.from(mContext);
		this.arrlist = new ArrayList<Contact>();
		this.arrlist.addAll(searchList);
	}

	public Yeuthich_Adapter() {

	}

	public class ViewHolder {
		TextView txtTitle;
		ImageView btnFavo;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return searchList.size();
	}

	@Override
	public Contact getItem(int position) {
		// TODO Auto-generated method stub
		return searchList.get(position);
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
			convertView = mInflater.inflate(R.layout.custom_like, null);
			holder.txtTitle = (TextView) convertView
					.findViewById(R.id.txtcustom_like);

			holder.btnFavo = (ImageView) convertView
					.findViewById(R.id.image_like);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txtTitle.setText(searchList.get(position).get_title());
		// holder.txtJpan.setText(searchList.get(position).getJapanese());
		// holder.txtPinyin.setText(searchList.get(position).getPinyin());
		holder.btnFavo.setImageResource(R.drawable.ic_action_favorite_active);
		holder.btnFavo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatabaseHandler db = new DatabaseHandler(mContext);
				String str = searchList.get(position).getId();
				db.getlikeupdate(str, "0");
				
				searchList.remove(position);
				notifyDataSetChanged();
			}
		});
		holder.txtTitle.setTag(position);

		holder.btnFavo.setTag(position);

		return convertView;
	}

}
