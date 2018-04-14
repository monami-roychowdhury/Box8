package com.monami.mrc.box8category;

public class DataModel {
//    String imageUrl;
    private String categoryName;
    private String productName;
    private String productPrice;
    private String productDesc;
    private String productType;

    public DataModel(){

    }

    public DataModel(String categoryName, String productName, String productPrice, String productDesc, String productType) {
        this.categoryName = categoryName;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDesc = productDesc;
        this.productType = productType;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
