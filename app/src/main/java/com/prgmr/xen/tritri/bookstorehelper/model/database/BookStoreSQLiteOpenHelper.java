package com.prgmr.xen.tritri.bookstorehelper.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookStoreSQLiteOpenHelper extends SQLiteOpenHelper {

	// static final String DB = "BookStoreHelper.db";
	static String DB;
	static String table;
	static final int DB_VERSION = 2;//DBテーブルの構成を変えた場合はこの値を増やしていく

	// static final String CREATE_TABLE =
	// "create table bookinfotable ( _id integer primary key autoincrement, title integer not null );";
	// static final String DROP_TABLE = "drop table mytable;";

	public BookStoreSQLiteOpenHelper(Context c, String dbName, String tableName) {
		super(c, dbName, null, DB_VERSION);
		DB = dbName;
		table = tableName;
	}

	/**
	 * データベースがない場合、新たに生成され。コンストラクタで指定したテーブルが作成されます。
	 */
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table "
				+ table
				+ " ( _id integer primary key autoincrement, title integer not null , isKindle numeric not null );");
	}

	/**
	 * データベースのバージョンアップがされたときに実行されます
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		this.DeleteTable(db);
		// db.execSQL("drop table " + table + ";");
		onCreate(db);
	}

	/**
	 * 
	 * @param db
	 *            Sqliteインスタンス
	 * @param tableName
	 *            生成するテーブル名（nullの場合はコンストラクタで指定されたテーブル名が使用されます）
	 */
	public void CreateTable(SQLiteDatabase db, String tableName) {
		try {
			if (tableName == null) {

				db.execSQL("create table "
						+ table
						+ " ( _id integer primary key autoincrement" +
						", title integer not null" +
						", isKindle numeric not null );");
			} else {

				db.execSQL("create table "
						+ tableName
						+ " ( _id integer primary key autoincrement" +
						", title integer not null" +
						", isKindle numeric not null );");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * DB内のテーブルを消去します
	 * 
	 * @param db
	 *            Sqliteインスタンス
	 */
	public void DeleteTable(SQLiteDatabase db) {
		db.execSQL("drop table " + table + ";");
	}

	/**
	 * テーブルにあるデータをすべて消去します
	 * 
	 * @param db
	 *            　Sqliteインスタンス
	 */
	public void DeleteTableData(SQLiteDatabase db) {
		try {
			db.beginTransaction();
			db.execSQL("DELETE FROM " + table);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			db.execSQL("VACUUM");
			//db.close();//データを消してもdbクローズはしないことにします
		}
	}
	/**
	 * dbのバージョン番号を取得します
	 * @param db
	 * @return
	 */
	public int GetVersion(SQLiteDatabase db) {
		return db.getVersion();
	}
	/**
	 * dbのバージョン番号をセットします
	 * @param db
	 * @param numberVersion
	 */
	public void SetVersion(SQLiteDatabase db,int numberVersion) {
		db.setVersion(numberVersion);
	}
}
