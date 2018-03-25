package com.prgmr.xen.tritri.bookstorehelper.model.AmazonAWSParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazon.advertising.api.sample.AWSRequestException;

public class AmazonISBNCodeHelper {
	private static String defaultHeader = "978";
	private static String countryCode = "4";

	public static String correctISBNcode(String code)
			throws AWSRequestException {
		String output;
		if (isContainOnlyNumerial(code)) {
			if (isNumber(code)) {
				switch (code.length()) {
				case 8:// 商品コードのみ
					output = defaultHeader + countryCode + code;
					output = output + makeCheckDegit(output);
					break;
				case 9:// 国コード+商品コード or 商品コード+チェックデジット
					if (code.indexOf(countryCode) == 0) {
						output = defaultHeader + code;
						output = output + makeCheckDegit(output);
					} else {
						output = defaultHeader + countryCode + code;
					}
					break;
				case 10:// 国コード+ 商品コード+チェックデジット
					output = defaultHeader + code;
					break;
				case 11:// ISBNコード+商品コード
					output = code.replaceAll(defaultHeader, "");
					output = defaultHeader + countryCode + output;
					output = output + makeCheckDegit(output);
					break;
				case 12:// ISBNコード+国コード+商品コード or ISBNコード+商品コード+チェックデジット
					output = code.replaceAll(defaultHeader, "");
					if (output.indexOf(countryCode) == 0) {
						output = defaultHeader + code;
						output = output + makeCheckDegit(output);
					} else {
						output = defaultHeader + countryCode + code;
					}
					break;
				case 13:// codeが１３桁全部そろっていた場合
					output = code;
					break;
				default:
					output = makeCheckDegit(code);
					output = "";
					throw new AWSRequestException("ISBNコードではありません", 0);
				}
			} else {
				throw new AWSRequestException("文字が混じっています", 0);
			}
		} else {
			throw new AWSRequestException("全角入力は行えません", 0);
		}
		return output;
	}
	public static boolean IsCorrectISBNcode(String code)
			throws AWSRequestException {
		boolean output=true;
		if (isContainOnlyNumerial(code)) {
			if (isNumber(code)) {
				if(code.length()<13){
					output=false;
					throw new AWSRequestException("ISBNコードではありません(コードが短すぎます)", 0);
				}else if(code.length()>13){
					output=false;
					throw new AWSRequestException("ISBNコードではありません(コードが長すぎます)", 0);
				}
			} else {
				output=false;
				throw new AWSRequestException("文字が混じっています", 0);
			}
		} else {
			output=false;
			throw new AWSRequestException("全角入力は行えません", 0);
		}
		return output;
	}
	/*
	 * 入力文字に対してＩＳＢＮコードで不足している部分を自動的に補完します
	 */
	public static String supplyHeader(String str) {
		String output;

		if (isTxtField(str)) {
			String header = defaultHeader + countryCode;
			String inputStr = ConvertOnlyNumerical(str);
			int strLength = inputStr.length();
			if (0 < strLength && strLength <= 4) {
				if (inputStr.equals(header.substring(0, strLength))) {
					output = inputStr;
				} else {
					output = header + inputStr;
				}
			}
			else if (11 < strLength) {
				String tmp = inputStr.substring(0, 12);
				output = tmp + makeCheckDegit(tmp);
			} else {
				output = inputStr;
			}
			output=insertIsbnDelimiter(output);
		} else {
			output = insertIsbnDelimiter(ConvertOnlyNumerical(str));
		}
		return output;
	}

	/*
	 * チェックデジットを計算します
	 */
	public static String makeCheckDegit(String s) {
		int odd = 0, even = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int a = Integer.parseInt("" + c);
			if (i % 2 == 0) {
				odd += a;
			} else {
				even += a;
			}
		}
		int degit = 10 - ((even * 3 + odd) % 10);
		if (degit < 0 || degit >= 10 ) {
			degit = 0;
		}
		return String.valueOf(degit);
	}
	public static String insertIsbnDelimiter(String input){
		String delimiter="-";
		String output="";
		int strLength= input.length();
		if(strLength==3 ){
			output =input+delimiter;
		}else if(4<=strLength && strLength<5 ){
			String s0 =input.substring(0, 3);
			String s1 =input.substring(4, input.length());
			output=s0+delimiter+s1;
		}else if(5<=strLength && strLength<9 ){
			String s0 =input.substring(0, 3);
			String s1 =input.substring(3, 4);
			String s2 =input.substring(4, input.length());
			output=s0+delimiter+s1+delimiter+s2;
		}else if(9<=strLength && strLength<13 ){
			String s0 =input.substring(0, 3);
			String s1 =input.substring(3, 4);
			String s2 =input.substring(4, 8);
			String s3 =input.substring(8, input.length());
			output=s0+delimiter+s1+delimiter+s2+delimiter+s3;
		}else if(13<=strLength){
			String s0 =input.substring(0, 3);
			String s1 =input.substring(3, 4);
			String s2 =input.substring(4, 8);
			String s3 =input.substring(8, 12);
			String s4 =input.substring(12, input.length());
			output=s0+delimiter+s1+delimiter+s2+delimiter+s3+delimiter+s4;
		}
		return output;
	}
	
	/*
	 * 数字以外の文字をすべて除去します
	 */
	public static String ConvertOnlyNumerical(String input) {
		String output=input.replaceAll("[^0-9]", "");
		//String output=input.replaceAll("^[^0-9]+$", "");
		return output;
	}

	/*
	 * 文字列が数字だけで構成されているか判定します
	 */
	public static boolean isContainOnlyNumerial(String s) {
		boolean output = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if ((c <= '\u007e') || // 英数字
					(c == '\u00a5') || // \記号
					(c == '\u203e') || // ~記号
					(c >= '\uff61' && c <= '\uff9f') // 半角カナ
			) {
				output = true;
			} else {
				output = false;
				break;
			}
		}
		return output;
	}

	/*
	 * 文字列が数字か-だけで構成されているか判定します
	 */
	public static boolean isTxtField(String Val) {
		Pattern p = Pattern.compile("^[0-9\\-]+$");
		Matcher m = p.matcher(Val);

		return m.find();
	}

	/*
	 * 文字列が数字だけで構成されているか判定します
	 */
	public static boolean isNumber(String val) {
		try {
			Double.parseDouble(val);
			return true;
		} catch (NumberFormatException nfex) {
			return false;
		}
	}
}
