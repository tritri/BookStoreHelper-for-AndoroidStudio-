package com.amazon.advertising.api.sample;

//import DOMParser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class ItemRequestParse {
	public static void Parse(Document document) {

		traceNodes(document.getFirstChild(), "Item", false);
		/*
		 * try { traceNodes(document.getFirstChild(),"Item",false);
		 * 
		 * } catch (SAXException e) { System.out.println("XML繝��繧ｿ縺御ｸ肴ｭ｣縺ｧ縺吶�");
		 * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
		 */
	}

	static private void traceNodes(Node node, String searchItem,
			boolean isDetect) {

		Node child = node.getFirstChild(); // 子ノードを取得する
		while (child != null) {
			String firstChildValue = child.getNodeName();
			if (firstChildValue.equals(searchItem)) {
				isDetect = true;
			}
			if (isDetect) {
				printNode(child); // とりあえずこのノードを表示
			}
			traceNodes(child, searchItem, isDetect); // 子ノードへ移る
			isDetect = false;// 帰ってきたらfalseにします

			child = child.getNextSibling(); // 兄弟ノードへ移る
		}
		/*
		 * while (child!=null) { String firstChildValue=child.getNodeName();
		 * if(isDetect){ printNode(child); // 子ノードがあれば表示
		 * traceNodes(child,firstChildValue,isDetect);// 続けて再帰的に子ノードへと移る }else{
		 * if(firstChildValue==searchItem){ isDetect=true; printNode(child); //
		 * とりあえずこのノードを表示 traceNodes(child,firstChildValue,isDetect); //子ノードへ移る
		 * isDetect=false;//帰ってきたらfalseにします } } child = child.getNextSibling();
		 * //兄弟ノードへ移る }
		 */
	}

	static private void printNode(Node node) {

		System.out.print("ノード = " + node.getNodeName() + " "); // 繝弱�繝牙錐
		System.out.print("ノードタイプ= " + node.getNodeType() + " "); // 繝弱�繝峨ち繧､繝�
		System.out.println("ノードの値 = " + node.getNodeValue()); // 繝弱�繝牙�

	}
}
