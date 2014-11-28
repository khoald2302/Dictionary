package com.mobile.android;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "databased";
	public final static String DATABASE_PATH = "/data/data/com.example.apptudien/databases/";

	private static final String TABLE_COMMUNICATION = "communicationn";
	protected static final String KEY_Id = "id";

	protected static final String KEY_title = "title";
	protected static final String KEY_attributes = "attributes";
	protected static final String KEY_like = "like";
	private static final String[] COLUMNS = { KEY_Id, KEY_title,
			KEY_attributes, KEY_like };

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	static String str="";

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_COMMUNICATION
				+ "(" + KEY_Id + " TEXT," + KEY_title + " TEXT,"
				+ KEY_attributes + " TEXT," + KEY_like + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// input data
	void addContact(Contact contatc) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_Id, contatc.getId());
		values.put(KEY_title, contatc.get_title());
		values.put(KEY_attributes, contatc.get_propeties());
		values.put(KEY_like, contatc.get_like());

		db.insert(TABLE_COMMUNICATION, null, values);
		db.close();
	}

	// access title
	Contact getContact(String title) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_COMMUNICATION, new String[] { KEY_title,
				KEY_attributes, KEY_like }, KEY_title + "=?",
				new String[] { title }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		Contact contact = new Contact(cursor.getString(1));

		return contact;
	}

	// getId_thông_qua_title
	Contact getContact_ID_in_Title(String title) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_COMMUNICATION, new String[] { KEY_Id },
				KEY_title + "=?", new String[] { title }, null, null, null,
				null);
		if (cursor != null)
			cursor.moveToFirst();
		Contact contact = new Contact();
		Log.i(null, "ID:" + cursor.getString(0));
		str=cursor.getString(0);
	
		return contact;
	}

	// update like
	public void getlike(String title) {
		SQLiteDatabase db = this.getReadableDatabase();
		ContentValues update = new ContentValues();
		update.put(KEY_like, 1);
		if (db.update(TABLE_COMMUNICATION, update, "title=?",
				new String[] { title }) != 0) {
			// Log.i(null, "đã update");
		} else {
			// Log.i(null, "lỗi");
		}
	}

	public void getlikeupdate(String id, String status) {
		SQLiteDatabase db = this.getReadableDatabase();
		ContentValues update = new ContentValues();
		update.put(KEY_like, status);
		if (db.update(TABLE_COMMUNICATION, update, "id=?", new String[] { id }) != 0) {
			// Log.i(null, "đã update");
		} else {
			// Log.i(null, "lỗi");
		}
	}

	public List<Contact> getAllContacts() {
		List<Contact> contactList = new ArrayList<Contact>();
		String selectQuery = "SELECT  * FROM " + TABLE_COMMUNICATION;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setId(cursor.getString(0));
				contact.set_title(cursor.getString(1));
				contact.set_propeties(cursor.getString(2));
				contact.set_like(cursor.getString(3));
				contactList.add(contact);
			} while (cursor.moveToNext());
			contactList.remove(0);
		}
		return contactList;
	}

	private Contact getPaRamSetDto(Cursor cursor) {
		int index = 0;
		Contact list = new Contact();
		String ItemList = cursor.getString(index++).trim();
		// Log.e("truyen", "mstruyen = " + ItemList);
		list.setId(ItemList);
		list.set_title(cursor.getString(index++).trim());
		list.set_propeties(cursor.getString(index++).trim());
		list.set_like(cursor.getString(index++).trim());

		return list;
	}

	public ArrayList<Contact> findByAll() {
		ArrayList<Contact> list = new ArrayList<Contact>();

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(TABLE_COMMUNICATION, COLUMNS, null, null,
				null, null, null);
		while (cursor.moveToNext()) {
			// Log.e("KhoaLD", "number");
			list.add(getPaRamSetDto(cursor));

		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}

	// update 1 dong trong bang TB_NAME = "status" voi gia tri cot id = id
	public boolean UPDATE_Status(String status, String id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_like, status);
		db.update(TABLE_COMMUNICATION, values, "id = " + id, null);
		return true;
	}

	public ArrayList<Contact> findByStatus(int status) {
		ArrayList<Contact> list = new ArrayList<Contact>();

		SQLiteDatabase db = this.getWritableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_COMMUNICATION + " WHERE "
				+ KEY_like + " = '" + status + "'";

		Cursor cursor = db.rawQuery(selectQuery, null);
		while (cursor.moveToNext()) {
			// Log.e("KhoaLD", "number");
			// Log.i(null, "databse:" + cursor.getString(1));
			list.add(getPaRamSetDto(cursor));
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}

	// delete database
	public void db_delete() {
		File file = new File(DATABASE_PATH + DATABASE_NAME);
		if (file.exists()) {
			file.delete();
			System.out.println("delete database file.");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion > oldVersion) {
			// Log.v("Database Upgrade", "Database version higher than old.");
			db_delete();
		}
	}
	// WHERE ten= ?",args
	// public List<class_Itemlike> getAllContactsLikee() {
	// List<class_Itemlike> contactList_ = new ArrayList<class_Itemlike>();
	// String str[] = { "1" };
	// SQLiteDatabase db = this.getWritableDatabase();
	// Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_COMMUNICATION
	// + " WHERE like=?", str);
	// if (cursor.moveToFirst()) {
	// do {
	// Log.e(null, "title:" + cursor.getString(0));
	// class_Itemlike clItemlike = new class_Itemlike();
	// class_Itemlike.clItemlike.setTitle(cursor.getString(0));
	// contactList_.add(clItemlike);
	// } while (cursor.moveToNext());
	// }
	// return contactList_;
	// }

	// public List<class_ItemChoose> search(String title) {
	// List<class_ItemChoose> list = new ArrayList<class_ItemChoose>();
	// SQLiteDatabase db = this.getWritableDatabase();
	// String selectQuery = "SELECT * FROM " + TABLE_COMMUNICATION + " WHERE "
	// + KEY_title + " = '" + title + "'";
	// Cursor cursor = db.rawQuery(selectQuery, null);
	// while (cursor.moveToNext()) {
	// Log.e("KhoaLD", "number");
	// class_ItemChoose.clChoose.setTitle(cursor.getString(0));
	// //list.add(cl);
	// }
	// if (cursor != null) {
	// cursor.close();
	// }
	// return list;
	// }
	// public List<Contact> searc(String title) {
	// SQLiteDatabase db = this.getWritableDatabase();
	// List<Contact> list = new ArrayList<Contact>();
	// Cursor c = db.query(TABLE_COMMUNICATION, new String[] { title },
	// "title=?", null, null, null, null);
	// String data = "";
	// c.moveToFirst();
	// while (!c.isAfterLast()) {
	// Contact contact = new Contact();
	// contact.set_title(c.getString(0));
	// // data+=c.getString(0)+"\n";
	// Log.i(null, "search:" + c.getString(0));
	// c.moveToNext();
	// }
	// list.add(Contact.contact);
	// Log.i(null, "search:" + title);
	// return list;
	// }

}
