package com.prgmr.xen.tritri.bookstorehelper.model;

import android.content.ContentValues;
import android.database.Cursor;

public interface ISqliteRecord {

	ContentValues GetRecord();

	void SetRecord(Cursor c);

	String GetDBName();

	String GetTableName();

	Object clone();

}
