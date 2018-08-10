package com.prgmr.xen.tritri.bookstorehelper.model.AmazonAWSParser;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class EncryptString {
	public static String ENCRYPT_KEY = "らんららんらんらんらんらんらんらんららんらんらんらららら";
    public static final String ENCRYPT_IV =  "アルプス一万弱小鑓の上でアルペン踊りをさあ踊りましょ";
    /**
    * 文字列をハッシュ化するメソッド
    *
    * @param text ハッシュ化するテキスト
    *
    * @return ハッシュ化した計算値(16進数)
    */
    public static String makeHashStr(String text) {
        // 変数初期化
        MessageDigest md = null;
        StringBuffer buffer = new StringBuffer();
     
        try {
            // メッセージダイジェストインスタンス取得
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            // 例外発生時、エラーメッセージ出力
            System.out.println("指定された暗号化アルゴリズムがありません");
        }
     
        // メッセージダイジェスト更新
        md.update(text.getBytes());
     
        // ハッシュ値を格納
        byte[] valueArray = md.digest();
     
        // ハッシュ値の配列をループ
        for(int i = 0; i < valueArray.length; i++){
            // 値の符号を反転させ、16進数に変換
            String tmpStr = Integer.toHexString(valueArray[i] & 0xff);
     
            if(tmpStr.length() == 1){
                // 値が一桁だった場合、先頭に0を追加し、バッファに追加
                buffer.append('0').append(tmpStr);
            } else {
                // その他の場合、バッファに追加
                buffer.append(tmpStr);
            }
        }
     
        // 完了したハッシュ計算値を返却
        return buffer.toString();
    }
    /**
     * 与えられた文字列から16bitのハッシュを出力します
     * @param str
     * @return
     */
    private static byte[] make16BitKey(String str)
    {
    	byte[] output=new byte[16];
        try {
        	String hashStr=makeHashStr(str);
    		byte[] byteText = hashStr.getBytes("UTF-8");
    		//ハッシュ値の先頭16bitのみ利用します
            for(int i=0;i<output.length;i++){
            	output[i]=byteText[i];
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return output;
    	
    }
	/**
     * 暗号化メソッド
     *
     * @param text 暗号化する文字列
     * @return 暗号化文字列
     */
    public static String encrypt(String text) {
        // 変数初期化
        String strResult = null;
 
        try {
            // 文字列をバイト配列へ変換
            byte[] byteText = text.getBytes("UTF-8");
 
            // 暗号化キーと初期化ベクトルをバイト配列へ変換

            byte[] byteKey = make16BitKey(ENCRYPT_KEY);
            byte[] byteIv = make16BitKey(ENCRYPT_IV);
            
            //byte[] byteKey = ENCRYPT_KEY.getBytes("UTF-8");
            //byte[] byteIv = ENCRYPT_IV.getBytes("UTF-8");
 
            // 暗号化キーと初期化ベクトルのオブジェクト生成
            SecretKeySpec key = new SecretKeySpec(byteKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(byteIv);
 
            // Cipherオブジェクト生成
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
 
            // Cipherオブジェクトの初期化
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
 
                        
            // 暗号化の結果格納
            byte[] byteResult = cipher.doFinal(byteText);
            strResult=new String(Base64.encodeBase64(byteResult));
            // Base64へエンコード
            //strResult = Base64.encodeBase64String(byteResult);
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }catch(Exception e) {
        	e.printStackTrace();;
        }
 
        // 暗号化文字列を返却
        return strResult;
    }
        /**
         * 復号化メソッド
         *
         * @param text 復号化する文字列
         * @return 復号化文字列
         */
        public static String decrypt(String text) {
            // 変数初期化
            String strResult = null;
     
            try {
                // Base64をデコード
            	byte[] strByte = text.getBytes("UTF-8");
            	
            	
                byte[] byteText = Base64.decodeBase64(strByte);
     
                // 暗号化キーと初期化ベクトルをバイト配列へ変換
                byte[] byteKey = make16BitKey(ENCRYPT_KEY);
                byte[] byteIv = make16BitKey(ENCRYPT_IV);
                //byte[] byteKey = ENCRYPT_KEY.getBytes("UTF-8");
                //byte[] byteIv = ENCRYPT_IV.getBytes("UTF-8");
     
                // 復号化キーと初期化ベクトルのオブジェクト生成
                SecretKeySpec key = new SecretKeySpec(byteKey, "AES");
                IvParameterSpec iv = new IvParameterSpec(byteIv);
     
                // Cipherオブジェクト生成
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
     
                // Cipherオブジェクトの初期化
                cipher.init(Cipher.DECRYPT_MODE, key, iv);
     
                // 復号化の結果格納
                byte[] byteResult = cipher.doFinal(byteText);
     
                // バイト配列を文字列へ変換
                strResult = new String(byteResult, "US-ASCII");
     
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
     
            // 復号化文字列を返却
            return strResult;
        }
}
