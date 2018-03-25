package com.prgmr.xen.tritri.bookstorehelper.model.database;

import java.util.ArrayList;
import java.util.List;

import com.prgmr.xen.tritri.bookstorehelper.model.ISqliteRecord;

import android.content.Context;
import android.database.Cursor;

/**
 * 
 * @author t_sakai
 * 
 * @param <BookRecord>
 *            データベースレコードクラス
 */
public class sqliteAccessBookRecord<T> extends sqliteAccess<Object> {

	private ISqliteRecord classInstance;
	/**
	 * コンストラクタ
	 * 
	 * @param context
	 *            コンテクスト
	 * @param data
	 *            レコードクラスのインスタンス（中身はどうでもいいです・・・）
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public sqliteAccessBookRecord(Context context, T data) {
		super(context);
		try {
			this.classInstance = (ISqliteRecord) data.getClass().newInstance();
			this.hlpr = new BookStoreSQLiteOpenHelper(
					context.getApplicationContext(),
					this.classInstance.GetDBName(),
					this.classInstance.GetTableName());
			mydb = hlpr.getWritableDatabase();
		} catch (InstantiationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	/**
	 * 指定したレコードを追加します
	 * 
	 * @param record
	 *            追加するレコード（ただし、型はコンストラクタで指定した型でないとエラーになります）
	 */
	public void addRecord(ISqliteRecord record) {
		// TODO 自動生成されたメソッド・スタブ

		if(mydb.insert("bookinfotable", null, record.GetRecord())==-1){
			;
		}
		// this.counter++;
	}

	/**
	 * テーブル内のすべてのデータを取得します
	 * 
	 * @return
	 */
	public Iterable<ISqliteRecord> getRecord() {

		Cursor c = mydb.query("bookinfotable", new String[] { "title","isKindle" }, null,
				null, null, null, null);
		c.moveToFirst();

		int recordLength = c.getCount();
		List<ISqliteRecord> dates = new ArrayList<ISqliteRecord>(recordLength);

		for (int i = 0; i < recordLength; i++) {

			ISqliteRecord result = (ISqliteRecord) this.classInstance.clone();

			result.SetRecord(c);
			dates.add(result);

			c.moveToNext();
		}
		c.close();
		return dates;
	}
}
