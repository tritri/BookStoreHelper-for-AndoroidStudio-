package com.prgmr.xen.tritri.bookstorehelper.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazon.advertising.api.sample.AWSRequestException;
import com.amazon.advertising.api.sample.ItemLookup;
import com.example.bookstorehelper.R;
import com.prgmr.xen.tritri.bookstorehelper.model.bookRecord;
import com.prgmr.xen.tritri.bookstorehelper.model.AmazonAWSParser.AmazonISBNCodeHelper;
import com.prgmr.xen.tritri.bookstorehelper.model.database.sqliteAccessBookRecord;

public class book_informationActivity extends Activity {

	Handler mHandler = new Handler(); // Handlerのインスタンス生成
	private static ProgressDialog waitDialog;

	public book_informationActivity() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_information);
		// TextViewにのインスタンスを取得
		final TextView LabelTitle = (TextView) findViewById(R.id.labelTitle);
		final TextView LabelAuthor = (TextView) findViewById(R.id.labelAuthor);
		final TextView LabelIsKindle = (TextView) findViewById(R.id.labelIsKindl);
		final TextView LabelEAN = (TextView) findViewById(R.id.labelEAN);
		final TextView LabelEANList = (TextView) findViewById(R.id.labelEANList);
		final TextView LabelListPrice = (TextView) findViewById(R.id.labelListPrice);
		final TextView LabelNumberOfPages = (TextView) findViewById(R.id.labelNumberOfPages);
		final TextView LabelPublicationDate = (TextView) findViewById(R.id.labelPublicationDate);
		final TextView LabelPublisher = (TextView) findViewById(R.id.labelPublisher);
		final TextView LabelReleaseDate = (TextView) findViewById(R.id.labelReleaseDate);
		final TextView LabelStudio = (TextView) findViewById(R.id.labelStudio);
		final TextView LabelDetailPageLink = (TextView) findViewById(R.id.labeldetailPageLink);

		//imageを取得
		final ImageView image = (ImageView) findViewById(R.id.imagebook);
		final Context baseContext = this.getBaseContext();

		// インテントより渡された値を取得します。
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {

			// プログレスダイアログの設定
			waitDialog = new ProgressDialog(this);
			// プログレスダイアログのメッセージを設定します
			waitDialog.setMessage("ネットワーク接続中...");
			// 円スタイル（くるくる回るタイプ）に設定します
			waitDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			// プログレスダイアログを表示
			waitDialog.show();
			final String isbn = AmazonISBNCodeHelper
					.ConvertOnlyNumerical(extras.getString("INPUT_STRING"));
			// スレッド起動
			(new Thread(new Runnable() {
				@Override
				public void run() {
					// 通常バックグランドをここに記述します
					//ここではWebアクセスとDBアクセスを別スレッドで実行します
					final ItemLookup item = new ItemLookup(baseContext);
					try {
						item.CallItemLookup(isbn);
						// DBへ取得したデータを保存します

						// DBのデータ読み取りオブジェクトインスタンスを作成
						sqliteAccessBookRecord<bookRecord> bookRecords = new sqliteAccessBookRecord<bookRecord>(
								baseContext, new bookRecord());

						bookRecord tmp = new bookRecord();
						tmp.SetTitle(item.Items.get(0).getTitle());
						tmp.SetIsKindle(item.Items.getIsKindle());
						bookRecords.addRecord(tmp);

						/**
						 * Handlerのpostメソッドを使ってUIスレッドに処理をdispatchします
						 */
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								// 取得したイメージをImageViewに設定
								LabelTitle
										.setText(item.Items.get(0).getTitle());
								LabelAuthor.setText(item.Items.get(0)
										.getAuthor());
								if (item.Items.getIsKindle()) {
									LabelIsKindle
											.setText(getString(R.string.kindleTrue));
								} else {
									LabelIsKindle
											.setText(getString(R.string.kindleFalse));
								}
								LabelEAN.setText(item.Items.get(0).getEAN());
								LabelEANList.setText(item.Items.get(0)
										.getEANList());
								LabelListPrice.setText(item.Items.get(0)
										.getListPrice());
								LabelNumberOfPages.setText(item.Items.get(0)
										.getNumberOfPages());
								LabelPublicationDate.setText(item.Items.get(0)
										.getPublicationDate());
								LabelPublisher.setText(item.Items.get(0)
										.getPublisher());
								LabelReleaseDate.setText(item.Items.get(0)
										.getReleaseDate());
								LabelStudio.setText(item.Items.get(0)
										.getStudio());
								LabelDetailPageLink.setText(item.Items.get(0)
										.getDetailPageURL());
								//画像取得スレッド起動
								ImageGetTask task = new ImageGetTask(image);
								task.execute(item.Items.get(0).getSmallImage());
								// プログレスダイアログ終了
								waitDialog.dismiss();
								waitDialog = null;
							}
						});
					} catch (AWSRequestException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
						final String errormessage = e.getErrorMessage();
						/**
						 * Handleionrのpostメソッドを使ってUIスレッドに処理をdispatchします
						 */
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								// プログレスダイアログ終了
								waitDialog.dismiss();
								waitDialog = null;
								Toast ts = Toast.makeText(
										book_informationActivity.this,
										errormessage, Toast.LENGTH_LONG);
								ts.show();
							}
						});

					} catch (Exception e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
						final String errormessage = e.getMessage();
						/**
						 * Handleionrのpostメソッドを使ってUIスレッドに処理をdispatchします
						 */
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								// プログレスダイアログ終了
								waitDialog.dismiss();
								waitDialog = null;
								Toast ts = Toast.makeText(
										book_informationActivity.this,
										errormessage, Toast.LENGTH_LONG);
								ts.show();
							}
						});

					}
				}
			})).start();
			
			
		} else {
		}

	}

}
