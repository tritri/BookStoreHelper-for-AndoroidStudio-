package com.prgmr.xen.tritri.bookstorehelper.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amazon.advertising.api.sample.AWSRequestException;
import com.example.bookstorehelper.R;
import com.prgmr.xen.tritri.bookstorehelper.model.UITextWatcher;
import com.prgmr.xen.tritri.bookstorehelper.model.bookRecord;
import com.prgmr.xen.tritri.bookstorehelper.model.AmazonAWSParser.AmazonISBNCodeHelper;
import com.prgmr.xen.tritri.bookstorehelper.model.database.sqliteAccessBookRecord;

public class MainActivity extends Activity implements OnClickListener {

	Button button;

	SharedPreferences sharedPref;

	sqliteAccessBookRecord<bookRecord> bookRecords;

	private EditText edittxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		button = (Button) findViewById(R.id.Inputbutton);
		button.setOnClickListener(this);

		sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		this.edittxt = (EditText) findViewById(R.id.editText1);
		this.edittxt.addTextChangedListener(new UITextWatcher(this.edittxt));
	}

	/**
	 * このアクティビティが再表示されたとき呼ばれます
	 */
	@Override
	protected void onResume() {
		super.onResume();
		try {
			// DBのデータ読み取りオブジェクトインスタンスを作成
			bookRecords = new sqliteAccessBookRecord<bookRecord>(this,
					new bookRecord());
			// Dbデータをすべて消去します
			// bookRecords.DeleteTable();
			// テーブルを新規に作成します
			// bookRecords.CreateTable();
			/*
			 * // bookRecords.DeleteTable(); // bookRecords.CreateTable();
			 */
			/*
			// テスト用データ作成
			for (int i = 0; i < 10; i++) {
				bookRecord tmp = new bookRecord();
				tmp.SetTitle("test" + i);
				if (i % 3 == 0) {
					tmp.SetIsKindle(true);
				}
				bookRecords.addRecord(tmp);
			}
			*/
			/*
			 * データベースのバージョンを取得します
			int test= bookRecords.GetDBVersion();
			*/
			// 画面表示アイテム（リスト表示のオブジェクト）作成
			LinearLayout layout = (LinearLayout) findViewById(R.id.sublayout);
			// レイアウト内のアイテムをすべて削除します
			layout.removeAllViews();
			// 画面にリストデータをDBから読み出し繰り返し表示
			for (Object i : bookRecords.getRecord()) {
				View view = getLayoutInflater().inflate(R.layout.sub, null);
				layout.addView(view);
				TextView text = (TextView) view.findViewById(R.id.text);
				text.setText(((bookRecord) i).getTitle());
				ImageView iconKindle = (ImageView) view
						.findViewById(R.id.subIconKindle);
				if (((bookRecord) i).GetIsKindle()) {
					iconKindle.setImageResource(R.drawable.kindle);
				} else {
					iconKindle.setImageResource(R.drawable.shelf);
				}
			}
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.optionsmenu, menu);
		return true;
	}

	protected void showMessage(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuitem1:
			startActivity(new Intent(this, MyPreferences.class));
			return true;
		case R.id.menuitem2:
			showMessage("Hello! Item2");
			Editor editor = sharedPref.edit();
			editor.putString("data1", "なんかようかい");
			editor.commit();
			return true;
		case R.id.menuitem3:
			showMessage(sharedPref.getString("data1", ""));
			return true;
		}
		return false;
	}

	public void onClick(View v) {
		try {
			String inputTxt = this.edittxt.getText().toString();
			inputTxt = AmazonISBNCodeHelper.ConvertOnlyNumerical(inputTxt);
			AmazonISBNCodeHelper.IsCorrectISBNcode(inputTxt);
			//Intentで別画面に遷移します
			Intent intent = new Intent(this, book_informationActivity.class);
			intent.putExtra("INPUT_STRING", inputTxt);
			startActivity(intent);
		} catch (AWSRequestException err) {
			Toast ts = Toast.makeText(this, err.getErrorMessage(),
					Toast.LENGTH_LONG);
			ts.show();
		}
	}

	/**
	 * activity_main上のEditTextがクリックされた場合のイベントハンドラ
	 *
	 * @param view
	 */
	public void onClickEditText(View view) {
		// this.edittxt.setText("Hello");
	}
}
