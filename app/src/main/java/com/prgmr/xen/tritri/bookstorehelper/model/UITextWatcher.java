package com.prgmr.xen.tritri.bookstorehelper.model;

import com.prgmr.xen.tritri.bookstorehelper.model.AmazonAWSParser.AmazonISBNCodeHelper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * EditTextが変更されたときに実行されるリスナークラス
 * 
 * @author tri
 * 
 */
public class UITextWatcher implements TextWatcher {

	private EditText edittxt;
	private boolean isChanged;
	private int ChangedCharCounter;
	private int beforeTxtLength;
	public UITextWatcher(EditText edittxt) {
		this.edittxt = edittxt;
		this.isChanged = false;
		new AmazonISBNCodeHelper();
		this.ChangedCharCounter = 0;
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO 自動生成されたメソッド・スタブ
		if (!this.isChanged) {
			String txt = this.edittxt.getText().toString();
			if (this.beforeTxtLength < txt.length()) {
				String supplyStr = AmazonISBNCodeHelper.supplyHeader(txt);
				this.isChanged = true;
				this.ChangedCharCounter = supplyStr.length();
				this.edittxt.setText(supplyStr);
				this.beforeTxtLength = supplyStr.length();
				this.isChanged = false;
				this.edittxt.setSelection(this.ChangedCharCounter);
			}else{
				this.beforeTxtLength = txt.length();
			}
		}
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
