package com.mobile.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TuVung_Activity extends Activity {

	TextView title;
	TextView pro;
	ImageView image;
	 public  String str3;

	@Override
	// thoat ra vo lai co hoi no chưa up lên man hinh
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tuvung_layout);
		title = (TextView) findViewById(R.id.txttitle);
		pro = (TextView) findViewById(R.id.txtpropeties);
		image = (ImageView) findViewById(R.id.imagview_1);
		image.setOnClickListener(new OnClickListener() {
			// thoi ta lam cho, 2'
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatabaseHandler db = new DatabaseHandler(TuVung_Activity.this);
				if (title.getText().equals("null")) {
					Toast.makeText(TuVung_Activity.this, "Chưa có từ cần like",
							Toast.LENGTH_SHORT).show();
				} else {
						db.getContact_ID_in_Title(title.getText().toString());			
						String str = Class_ID.class_ID.getID();
						Log.i(null, "str:"+db.str);
						db.getlikeupdate(db.str, "1");
					}
		
			}
		});

		Intent intent = this.getIntent();
		String str1 = intent.getStringExtra("title");
		String str2 = intent.getStringExtra("pro");
		str3= intent.getStringExtra("id");
		title.setText("" + str1);
		pro.setText("" + str2);

	}
}
