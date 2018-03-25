package com.amazon.advertising.api.sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.prgmr.xen.tritri.bookstorehelper.model.AmazonAWSParser.AmazonAWSItemAttribute;
import com.prgmr.xen.tritri.bookstorehelper.model.AmazonAWSParser.AmazonAWSItems;
import com.prgmr.xen.tritri.bookstorehelper.model.AmazonAWSParser.AmazonISBNCodeHelper;
import com.prgmr.xen.tritri.bookstorehelper.model.AmazonAWSParser.EncryptString;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

public class ItemLookup {
	// public class ItemLookup extends AsyncTask<String, Void, String>{

	/*
	 * Use one of the following end-points, according to the region you are
	 * interested in:
	 * 
	 * US: ecs.amazonaws.com CA: ecs.amazonaws.ca UK: ecs.amazonaws.co.uk DE:
	 * ecs.amazonaws.de FR: ecs.amazonaws.fr JP: ecs.amazonaws.jp
	 */
	private static final String ENDPOINT = "ecs.amazonaws.jp";

	private Context content;

	public String Title;
	/**
	 * 取得したItemAttribute
	 */
	public AmazonAWSItems<AmazonAWSItemAttribute> Items;

	public String ErrorMessage;

	// private Activity mainActivity;

	public ItemLookup(Context content) {

		this.content = content;
		// this.mainActivity=runnable;
	}

	/*
	 * // このメソッドは必ずオーバーライドする必要があるよ // ここが非同期で処理される部分みたいたぶん。
	 * 
	 * @Override protected String doInBackground(String... item) { //
	 * httpリクエスト投げる処理を書く。 // ちなみに私はHttpClientを使って書きましたー
	 * this.CallItemLookup(item[0]); return this.Title; }
	 * 
	 * 
	 * // このメソッドは非同期処理の終わった後に呼び出されます
	 * 
	 * @Override protected void onPostExecute(String result) {
	 * 
	 * }
	 */

	public void CallItemLookup(String ITEM_ID) throws AWSRequestException,
			InvalidKeyException, IllegalArgumentException,
			UnsupportedEncodingException, NoSuchAlgorithmException {
		/*
		 * Set up the signed requests helper
		 */

		//ITEM_ID = "90686681";// 8桁しかないその１（デフォルト、国コード、チェックデジットがない）
		//ITEM_ID = "83225175";// 8桁しかないその2（デフォルト、国コード、チェックデジットがない）
		//ITEM_ID = "456995111617";// テスト用コード）
		//ITEM_ID = "9784906866816";//13桁コードその１(Kindleあり)
		//ITEM_ID = "9784832251755";//13桁コードその２(Kindleなし)
		ITEM_ID=AmazonISBNCodeHelper.correctISBNcode(ITEM_ID);
		
		try {
			SignedRequestsHelper helper;
			SharedPreferences sharedPref = PreferenceManager
					.getDefaultSharedPreferences(content);

			// preferenceのデータをすべて消去します
			// sharedPref.edit().clear().commit();

			// preferenceからキーを取得します
			String encryptPublicKeyString = sharedPref.getString(
					"AWS PublicKey", "");
			String encryptSecretKeyString = sharedPref.getString(
					"AWS SeacretKey", "");
			String encryptAssociateString = sharedPref.getString(
					"AssociateTag", "");
			String passwordHash = sharedPref.getString("password", "");

			// パスワードハッシュをセットします
			EncryptString.ENCRYPT_KEY = passwordHash;
			// 暗号化されたキーをデコードします
			String decryptPublicKeyString = EncryptString
					.decrypt(encryptPublicKeyString);
			String decryptSecretKeyString = EncryptString
					.decrypt(encryptSecretKeyString);
			String decryptAssociateTagString = EncryptString
					.decrypt(encryptAssociateString);

			helper = SignedRequestsHelper.getInstance(ENDPOINT,
					decryptPublicKeyString, decryptSecretKeyString);

			String requestUrl = null;

			System.out.println("Map form example:");
			Map<String, String> params = new HashMap<String, String>();
			params.put("Service", "AWSECommerceService");
			params.put("Version", "2011-08-01");
			params.put("Operation", "ItemLookup");
			params.put("ItemId", ITEM_ID);
			params.put("IdType", "ISBN");
			params.put("SearchIndex", "All");
			params.put("Condition", "All");
			params.put("ResponseGroup", "Images,ItemAttributes,Offers");
			params.put("AssociateTag", decryptAssociateTagString);

			requestUrl = helper.sign(params);
			this.Items = getItemAttribute(requestUrl);
		} catch (AWSRequestException e) {
			e.printStackTrace();
			throw e;
		}
	}
	/*
	 * ItemAttributeクラス用データを取得します
	 */
	private AmazonAWSItems<AmazonAWSItemAttribute> getItemAttribute(
			String requestUrl) throws AWSRequestException {
		AmazonAWSItems<AmazonAWSItemAttribute> output = null;

		// AmazonAWSItemAttribute item;
		Document doc = null;
		try {
			if (netWorkCheck(content)) {
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				doc = db.parse(requestUrl);

				ItemRequestParse.Parse(doc);
				output = new AmazonAWSItems<AmazonAWSItemAttribute>(doc);
			} else {
				throw new AWSRequestException("NotConnetedNetWork", 0);
			}

		} catch (NullPointerException e) {
			Node errorMessageNode = doc.getElementsByTagName("Message").item(0);
			this.ErrorMessage = errorMessageNode.getTextContent();
			throw new AWSRequestException(this.ErrorMessage, 0);
			// output=this.ErrorMessage;
		} catch (Exception e) {
			throw new AWSRequestException(e.getMessage(), 0);
			// throw new RuntimeException(e);
		}
		return output;
	}

	// ネットワーク接続確認
	public static boolean netWorkCheck(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null) {
			return info.isConnected();
		} else {
			return false;
		}
	}

	/*
	 * /* Utility function to fetch the response from the service and extract
	 * the title from the XML.
	 */
	/*
	 * private String fetchTitle(String requestUrl) { String output=""; Document
	 * doc=null; try { DocumentBuilderFactory dbf =
	 * DocumentBuilderFactory.newInstance(); DocumentBuilder db =
	 * dbf.newDocumentBuilder(); doc = db.parse(requestUrl);
	 * 
	 * ItemRequestParse.Parse(doc);
	 * 
	 * //NodeList nodeList = doc.getChildNodes(); //nodelists=
	 * nodeList.item(0).getChildNodes();
	 * 
	 * //int length = nodeList.getLength(); //String test; //for(int
	 * i=0;i<length;i++){ // test= nodeList.item(i).getNodeName(); //}
	 * 
	 * this.Items =new AmazonAWSItems<AmazonAWSItemAttribute>(doc);
	 * 
	 * Node titleNode = doc.getElementsByTagName("Title").item(0);
	 * output=titleNode.getTextContent(); }catch(NullPointerException e){ Node
	 * errorMessageNode = doc.getElementsByTagName("Message").item(0);
	 * this.ErrorMessage = errorMessageNode.getTextContent();
	 * output=this.ErrorMessage; } catch (Exception e) { throw new
	 * RuntimeException(e); } return output; }
	 */
	/**
	 * AWSログイン用ルートキークラスをシリアライズします
	 * 
	 * @param content
	 */
	@SuppressWarnings("unused")
	private void serializeInformationAWS(Context content) {
		try {
			FileOutputStream fs = content.openFileOutput("myfile.txt",
					Context.MODE_PRIVATE);
			ObjectOutputStream objOutStream = new ObjectOutputStream(fs);

			InformationRestAmazonAWSSerialize info = new InformationRestAmazonAWSSerialize();

			objOutStream.writeObject(info);
			objOutStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * シリアライズされているAWSログイン用ルートキークラスをデコードします
	 * 
	 * @param content
	 * @return
	 */
	@SuppressWarnings("unused")
	private InformationRestAmazonAWSSerialize deSerializeInformationAWS(
			Context content) {
		InformationRestAmazonAWSSerialize aws = null;
		try {

			FileInputStream fs = content.openFileInput("myfile.txt");
			ObjectInputStream objInStream = new ObjectInputStream(fs);
			aws = (InformationRestAmazonAWSSerialize) objInStream.readObject();
			objInStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return aws;

	}
}
