package com.prgmr.xen.tritri.bookstorehelper.model;

import android.content.ContentValues;
import android.database.Cursor;

public class bookRecord implements ISqliteRecord, Cloneable {
	/**
	 * このレコードを格納するDB名を決定します
	 */
	private static final String defaultDBName = "BookStoreHelper.db";
	private static final String defaultTableName = "bookinfotable";

	/**
	 * コンストラクタ
	 */
	public bookRecord() {
		this.tableName = defaultTableName;
		this.dbName = defaultDBName;
	}

	private String tableName;

	/**
	 * DB名を返します
	 * 
	 * @return　テーブル名
	 */
	public String GetTableName() {
		return this.tableName;
	}

	private String dbName;

	/**
	 * テーブル名を返します
	 * 
	 * @return　テーブル名
	 */
	public String GetDBName() {
		return this.dbName;
	}

	private String title;

	/**
	 * レコードのタイトル名を取得します
	 * 
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * レコードにタイトル名をセットします
	 * 
	 * @param value
	 */
	public void SetTitle(String value) {
		this.title = value;
	}

	private boolean isKindle;

	/**
	 * Kindle化されているかを取得します
	 * 
	 * @return
	 */
	public boolean GetIsKindle() {
		return this.isKindle;
	}

	/**
	 * Kindle化されているかをセットします
	 * 
	 * @param value
	 */
	public void SetIsKindle(boolean value) {
		this.isKindle = value;
	}

	public ContentValues GetRecord() {

		ContentValues values = new ContentValues();
		values.put("title", this.title);
		values.put("isKindle", this.isKindle);
		return values;
	}

	public void SetRecord(Cursor c) {

		title = c.getString(0);
		if (c.getString(1).equals("1")) {
			isKindle = true;
		} else {
			isKindle = false;
		}
	}

	public Object clone() {
		Object r;
		try {
			r = super.clone();
		} catch (CloneNotSupportedException ce) {
			throw new RuntimeException();/* 適切なエラー処理 */
		}
		return r;
	}
}
