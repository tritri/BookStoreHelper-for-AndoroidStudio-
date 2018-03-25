package com.amazon.advertising.api.sample;
/**
 * AWSリクエスト時に発生したエラーに関しての例外クラス
 * @author t_sakai
 *
 */
@SuppressWarnings("serial")
public class AWSRequestException extends Exception {
	
	 private int errorLevel;
	 public int getErrorLevel(){
		 return this.errorLevel;
	 }

	 private String errorMessage;

	 public String getErrorMessage(){
		 return this.errorMessage;
	 }
	
	public AWSRequestException(String errorMessage,int errorLevel){
		
		
		this.errorMessage=errorMessage;
		this.errorLevel=errorLevel;
	}

}
