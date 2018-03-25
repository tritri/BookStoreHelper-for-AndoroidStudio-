package com.prgmr.xen.tritri.bookstorehelper.model.database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class sqliteAccess<T> implements Iterable<T> {

	List<T> dates;

	SQLiteDatabase mydb;

	protected BookStoreSQLiteOpenHelper hlpr;
	/**
	 * コンストラクタ
	 * 
	 * @param parent
	 */
	public sqliteAccess(Context parent) {
		this.dates = new ArrayList<T>();

	}

	public void add(T data) {
		// TODO 自動生成されたメソッド・スタブ
		this.dates.add(data);
	}


	public Iterator<T> iterator() {
		// TODO 自動生成されたメソッド・スタブ
		return dates.iterator();
	}

	public void DeleteAllData() {
		this.hlpr.DeleteTableData(mydb);
	}
	public void DeleteTable(){
		this.hlpr.DeleteTable(mydb);
	}
	public void CreateTable(){
		this.hlpr.CreateTable(mydb, null);
	}
	public int GetDBVersion(){
		return this.hlpr.GetVersion(mydb);
	}
	public void SetDBVersion(int number){
		this.hlpr.SetVersion(mydb, number);
	}

}