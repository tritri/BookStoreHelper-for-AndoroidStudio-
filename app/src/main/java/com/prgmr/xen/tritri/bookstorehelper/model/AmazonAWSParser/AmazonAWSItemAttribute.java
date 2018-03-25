package com.prgmr.xen.tritri.bookstorehelper.model.AmazonAWSParser;

public class AmazonAWSItemAttribute {
	
	
	 private String title;
	 /**
	  * 題名取得
	  * @return
	  */
	 public String getTitle(){
		 return this.title;
	 }
	 /**
	  * 題名挿入
	  * @param value
	  */
	 public void setTitle(String value){
		 this.title=value;
	 }
	 
	 
	 private String author;

	 public String getAuthor(){
		 return this.author;
	 }

	 public void setAuthor(String value){
		 this.author=value;
	 }
	 
	 private String binding;

	 public String getBinding(){
		 return this.binding;
	 }

	 public void setBinding(String value){
		 this.binding=value;
	 }
	 
	 private String ean;

	 public String getEAN(){
		 return this.ean;
	 }

	 public void setEAN(String value){
		 this.ean=value;
	 }
	 
	 private String eanList;

	 public String getEANList(){
		 return this.eanList;
	 }

	 public void setEANList(String value){
		 this.eanList=value;
	 }
	 
	 private String isAdultProduct;

	 public String getIsAdultProduct(){
		 return this.isAdultProduct;
	 }

	 public void setIsAdultProduct(String value){
		 this.isAdultProduct=value;
	 }
	 
	 private String isbn;

	 public String getISBN(){
		 return this.isbn;
	 }

	 public void setISBN(String value){
		 this.isbn=value;
	 }
	 
	 private String label;

	 public String getLabel(){
		 return this.label;
	 }

	 public void setLabel(String value){
		 this.label=value;
	 }
	 
	 private String languages;

	 public String getlanguages(){
		 return this.languages;
	 }

	 public void setlanguages(String value){
		 this.languages=value;
	 }
	 
	 private String listPrice;

	 public String getListPrice(){
		 return this.listPrice;
	 }

	 public void setListPrice(String value){
		 this.listPrice=value;
	 }
	 
	 private String currencyCode;

	 public String getCurrencyCode(){
		 return this.currencyCode;
	 }

	 public void setCurrencyCode(String value){
		 this.currencyCode=value;
	 }
	 
	 private String formattedPrice;

	 public String getFormattedPrice(){
		 return this.formattedPrice;
	 }

	 public void setFormattedPrice(String value){
		 this.formattedPrice=value;
	 }
	 
	 private String manufacturer;

	 public String getManufacturer(){
		 return this.manufacturer;
	 }

	 public void setManufacturere(String value){
		 this.manufacturer=value;
	 }
	 
	 private String numberOfPages;

	 public String getNumberOfPages(){
		 return this.numberOfPages;
	 }

	 public void setNumberOfPages(String value){
		 this.numberOfPages=value;
	 }
	 
	 private String height;

	 public String getHeight(){
		 return this.height;
	 }

	 public void setHeight(String value){
		 this.height=value;
	 }
	 
	 private String length;

	 public String getLength(){
		 return this.length;
	 }

	 public void setLength(String value){
		 this.length=value;
	 }
	 
	 private String weight;

	 public String getWeight(){
		 return this.weight;
	 }

	 public void setWeight(String value){
		 this.weight=value;
	 }
	 
	 private String width;

	 public String getWidth(){
		 return this.width;
	 }

	 public void setWidth(String value){
		 this.width=value;
	 }
	 
	 private String productGroup;

	 public String getProductGroup(){
		 return this.productGroup;
	 }

	 public void setProductGroup(String value){
		 this.productGroup=value;
	 }
	 
	 private String productTypeName;

	 public String getProductTypeName(){
		 return this.productTypeName;
	 }

	 public void setProductTypeName(String value){
		 this.productTypeName=value;
	 }
	 
	 private String publicationDate;

	 public String getPublicationDate(){
		 return this.publicationDate;
	 }

	 public void setPublicationDate(String value){
		 this.publicationDate=value;
	 }
	 
	 private String publisher;

	 public String getPublisher(){
		 return this.publisher;
	 }

	 public void setPublisher(String value){
		 this.publisher=value;
	 }
	 
	 private String releaseDate;

	 public String getReleaseDate(){
		 return this.releaseDate;
	 }

	 public void setReleaseDate(String value){
		 this.releaseDate=value;
	 }
	 
	 private String studio;

	 public String getStudio(){
		 return this.studio;
	 }

	 public void setStudio(String value){
		 this.studio=value;
	 }
	 
	 private String detailPageURL;

	 public String getDetailPageURL(){
		 return this.detailPageURL;
	 }

	 public void setDetailPageURL(String value){
		 this.detailPageURL=value;
	 }
	 
	 private String smallImage;

	 public String getSmallImage(){
		 return this.smallImage;
	 }

	 public void setSmallImage(String value){
		 this.smallImage=value;
	 }
	 
	 

	 private boolean isError;

	 public boolean getIsError(){
		 return this.isError;
	 }

	 public void setIsError(boolean value){
		 this.isError=value;
	 }
	 
	 private String errorMessage;

	 public String getErrorMessage(){
		 return this.errorMessage;
	 }

	 public void setErrorMessage(String value){
		 this.errorMessage=value;
	 }	 
	 
	 
	 
	 /**
	  * 
	  */
	 public AmazonAWSItemAttribute()
	 {
		 this.isError=false;
	 }
}
