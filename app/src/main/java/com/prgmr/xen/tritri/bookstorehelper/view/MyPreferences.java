package com.prgmr.xen.tritri.bookstorehelper.view;

import com.example.bookstorehelper.R;
import com.prgmr.xen.tritri.bookstorehelper.model.AmazonAWSParser.EncryptString;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import android.preference.EditTextPreference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference;

public class MyPreferences extends PreferenceActivity {

	public String DecryptPublicKeyString;
	public String DecryptSecretKeyString;
	public String DecryptAssociateTagString;

	SharedPreferences sharedPref;

	// テキストボックスPreferenceの PreferenceChangeリスナー
	private OnPreferenceChangeListener editTextPreference_OnPreferenceChangeListener =
			new OnPreferenceChangeListener(){
				@Override
				public boolean onPreferenceChange(Preference preference, Object newValue) {
					return editTextPreference_OnPreferenceChange(preference,newValue);
				}};

	private boolean editTextPreference_OnPreferenceChange(Preference preference, Object newValue){
		String input = newValue.toString();
		if (input != null && Integer.parseInt(input) > 100){
			//nullでなく100以上であれば要約を変更する
			preference.setSummary(input);
			return true;
		} else {
			//nullまたは100以下はエラー
			return false;
		}
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

		// キーを基に、テキストボックス設定のインスタンスを取得する
		CharSequence cs =getText(R.string.awsPublicKey);
		EditTextPreference  etp = (EditTextPreference)findPreference(cs);
		// リスナーを設定する
		etp.setOnPreferenceChangeListener(editTextPreference_OnPreferenceChangeListener);






		// preferenceのデータをすべて消去します
		// sharedPref.edit().clear().commit();

		// preferenceからキーを取得します
		try {
			String encryptPublicKeyString = this.sharedPref.getString(
					"AWS PublicKey", "");
			String encryptSecretKeyString = this.sharedPref.getString(
					"AWS SeacretKey", "");
			String encryptAssociateString = this.sharedPref.getString(
					"AssociateTag", "");
			String passwordHash = this.sharedPref.getString("password", "");

			// パスワードハッシュをセットします
			EncryptString.ENCRYPT_KEY = passwordHash;
			// 暗号化されたキーをデコードします
			DecryptPublicKeyString = EncryptString
					.decrypt(encryptPublicKeyString);
			DecryptSecretKeyString = EncryptString
					.decrypt(encryptSecretKeyString);
			DecryptAssociateTagString = EncryptString
					.decrypt(encryptAssociateString);
		} catch (Exception e) {
			Resources res = getResources();
			Toast.makeText(this, res.getString(R.string.messageEncriptDataError), Toast.LENGTH_LONG).show();

		}
		/*
		 * //preferenceにデータをセットし表示に反映します Editor editor = sharedPref.edit();
		 * editor.putString("AWS PublicKey", encryptPublicKeyString);
		 * editor.putString("AWS SeacretKey", encryptSecretKeyString);
		 * editor.commit();
		 */

		addPreferencesFromResource(R.xml.preferences);
	}

	protected void findViews() {

	}

	@Override
	public void onStart() {
		super.onStart();
		Log.v("LifeCycle", "onStart");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.v("LifeCycle", "onResume");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.v("LifeCycle", "onPause");
	}

	@Override
	public void onRestart() {
		super.onRestart();
		Log.v("LifeCycle", "onRestart");
	}

	@Override
	public void onStop() {
		super.onStop();

		// パスワードをハッシュ化して使用します
		String passwordHash = this.sharedPref.getString("password", "");

		// 各キーをプリファレンスより取得します
		String publicKey = this.sharedPref.getString("AWS PublicKey", "");
		String secretKey = this.sharedPref.getString("AWS SeacretKey", "");
		String associateTag = this.sharedPref.getString("AssociateTag", "");

		String encryptPublicKey= EncryptString.encrypt(publicKey );
		String encryptSecretKey= EncryptString.encrypt(secretKey );
		String encryptAssociateTag=EncryptString.encrypt(associateTag );

		// 暗号化したキーをpreferenceに保存します
		Editor editor = sharedPref.edit();

		editor.putString("AWS PublicKey", encryptPublicKey);
		editor.putString("AWS SeacretKey", encryptSecretKey);
		editor.putString("AssociateTag", encryptAssociateTag);
		editor.putString("password", passwordHash);

		boolean commit = editor.commit();

		Log.v("LifeCycle", "onStop");
	}

	/*
	 * @Override public void onStop(){ super.onStop();
	 * 
	 * //パスワードをハッシュ化して使用します String password
	 * =this.sharedPref.getString("password", "") ; String passwordHash =
	 * EncryptString.makeHashStr(password);
	 * EncryptString.ENCRYPT_KEY=passwordHash;
	 * 
	 * //各キーを暗号化します String publicKey= this.sharedPref.getString("AWS PublicKey",
	 * "") ; String encryptPublicKey= EncryptString.encrypt(publicKey); String
	 * secretKey= this.sharedPref.getString("AWS SeacretKey", "") ; String
	 * encryptSecretKey= EncryptString.encrypt(secretKey);
	 * 
	 * //暗号化したキーをpreferenceに保存します Editor editor = sharedPref.edit();
	 * editor.putString( "AWS PublicKey", encryptPublicKey); editor.putString(
	 * "AWS SeacretKey", encryptSecretKey); editor.putString("password",
	 * passwordHash);
	 * 
	 * editor.commit();
	 * 
	 * Log.v("LifeCycle", "onStop"); }
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.v("LifeCycle", "onDestroy");
	}
}
