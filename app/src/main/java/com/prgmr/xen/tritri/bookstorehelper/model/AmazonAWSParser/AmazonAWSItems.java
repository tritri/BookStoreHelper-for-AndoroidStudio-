package com.prgmr.xen.tritri.bookstorehelper.model.AmazonAWSParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;

public class AmazonAWSItems<T> {

	/**
	 * 内部変数
	 */
	List<AmazonAWSItemAttribute> datas;
	private boolean isKindle;

	/**
	 * キンドル版が存在するかどうか
	 * 
	 * @return
	 */
	public boolean getIsKindle() {
		return this.isKindle;
	}

	/**
	 * コンストラクタ
	 */
	public AmazonAWSItems(Document document) {

		this.isKindle = false;

		datas = new ArrayList<AmazonAWSItemAttribute>();
		List<HashMap<String, String>> getDatas = AmazonRequestXmlParser
				.GetChildSearchedNodes(document, "Item");
		//List<HashMap<String, String>> getDatas = AmazonRequestXmlParser
		//		.GetChildSearchedNodes(document, "ItemAttributes");

		for (HashMap<String, String> gatData : getDatas) {
			if (!gatData.isEmpty()) {
				AmazonAWSItemAttribute tmppppdata = new AmazonAWSItemAttribute();
				tmppppdata.setAuthor(gatData.get("Author"));
				tmppppdata.setBinding(gatData.get("Binding"));
				tmppppdata.setCurrencyCode(gatData.get("CurrencyCode"));
				tmppppdata.setEAN(gatData.get("EAN"));
				tmppppdata.setEANList(gatData.get("EANList"));
				tmppppdata.setFormattedPrice(gatData.get("FormattedPrice"));
				tmppppdata.setHeight(gatData.get("Height"));
				tmppppdata.setIsAdultProduct(gatData.get("IsAdultProduct"));
				tmppppdata.setISBN(gatData.get("ISBN"));
				tmppppdata.setLabel(gatData.get("Label"));
				tmppppdata.setlanguages(gatData.get("Languages"));
				tmppppdata.setLength(gatData.get("Length"));
				tmppppdata.setListPrice(gatData.get("ListPrice"));
				tmppppdata.setManufacturere(gatData.get("Manufacturere"));
				tmppppdata.setNumberOfPages(gatData.get("NumberOfPages"));
				tmppppdata.setProductGroup(gatData.get("ProductGroup"));
				tmppppdata.setProductTypeName(gatData.get("ProductTypeName"));
				tmppppdata.setPublicationDate(gatData.get("PublicationDate"));
				tmppppdata.setPublisher(gatData.get("Publisher"));
				tmppppdata.setReleaseDate(gatData.get("ReleaseDate"));
				tmppppdata.setStudio(gatData.get("Studio"));
				tmppppdata.setTitle(gatData.get("Title"));
				tmppppdata.setWeight(gatData.get("Weight"));
				tmppppdata.setWidth(gatData.get("Width"));
				tmppppdata.setDetailPageURL(gatData.get("DetailPageURL"));
				tmppppdata.setSmallImage(gatData.get("SmallImage"));
				datas.add(tmppppdata);
				// キンドル版があるかどうかの判定
				if (tmppppdata.getBinding().equals("Kindle版")) {
					this.isKindle = true;
				}
			}
		}

	}

	public AmazonAWSItems() {
	}

	@SuppressWarnings("unchecked")
	public T get(int index) {
		return (T) datas.get(index);
	}

	public void add(T e) {
		datas.add((AmazonAWSItemAttribute) e);
	}
}
