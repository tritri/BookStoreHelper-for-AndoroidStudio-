package com.prgmr.xen.tritri.bookstorehelper.model.AmazonAWSParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;


public class AmazonRequestXmlParser {
    /**
 * 指定した検索ワードに合致したノードのそれ以下の階層にあるノードデータを取得します
 * @param document
 * @param searchWords
 */
 static public List<HashMap<String,String>> GetChildSearchedNodes(Document document,String searchWords){

	 
       List<HashMap<String,String>> output = new ArrayList<HashMap<String,String>>();
       output.add(new HashMap<String,String>());
       traceNodes(output,document.getFirstChild(),searchWords, false,0,100000);
       return output;
}
 /**
 * このメソッドで指定ワードに合致したノードについて再帰的に階層を掘っていきます
 * @param node
 * @param searchItem
 * @param isDetect
 * @param currentRank
 * @param searchDetectRank
 */
 static private void traceNodes(List<HashMap<String,String>> nodeGetData,Node node,
		 String searchItem, boolean isDetect,int currentRank,int searchDetectRank) {
       Node child=node.getFirstChild();      // 子ノードを取得する
 currentRank++; //現状階層を一つ上げます
 while (child!=null ) {//同階層のノード(兄弟)ノード表示し続けます
        if(currentRank==searchDetectRank){
        	nodeGetData.add(new HashMap<String,String>());
              isDetect= false;//現状の階層と検索開始階層が同じなら、ノード表示フラグをfalseに戻します
               //printNode(child,thisRank);                // とりあえずこのノードを表示
        }
        String firstChildValue=child.getNodeName();
        if(firstChildValue.equals(searchItem))
        {   
            isDetect= true;//検索ノードが見つかったら、ノード表示フラグをtrueとします
            searchDetectRank=currentRank; //検索文字列が見つかった階層番号を保存します
        }
        if(isDetect){
               //printNode(child,currentRank);                // ノード表示フラグが立っている限り、ノードの値を表示し続けます
               if(nodeGetData!=null && nodeGetData.size()>0){
            	   getNodeData(child,nodeGetData);
               }
        }
        traceNodes(nodeGetData,child,searchItem,isDetect,currentRank,searchDetectRank);               //子ノードへ移る
        child = child.getNextSibling();  //兄弟ノードへ移る
    }
 currentRank--; //兄弟ノードをすべて表示し、この階層から抜けるときに階層を一つ下げます
  }
 static private void getNodeData(Node node,List<HashMap<String,String>> nodeGetData){
	 int nowIndex=nodeGetData.size()-1;
	 if((nodeGetData.get(nowIndex)).containsKey(node.getNodeName())){
		 String tmpstr=(nodeGetData.get(nowIndex)).get(node.getNodeName())
				 +","+node.getTextContent();//同じキーがある場合はカンマで区切ってデータを一つのキーにまとめます
		 (nodeGetData.get(nowIndex)).put(node.getNodeName(), tmpstr);
	 }else{
		 (nodeGetData.get(nowIndex)).put(node.getNodeName(), node.getTextContent());
	 }
 }

}