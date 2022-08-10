package com.jp.tech.test.entity;

public class AbstractSaleMessage implements SaleMessage {
private String productType;
private Double saleValue; // this is in assuming the values will be accumulated to large

    public AbstractSaleMessage(String productType, Double saleValue) {
        this.productType = productType;
        this.saleValue = saleValue;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Double getSaleValue() {
        return saleValue;
    }

    public void setSaleValue(Double saleValue) {
        this.saleValue = saleValue;
    }

    @Override
    public String toString() {
        return "{" + "productType='" + productType + '\'' + ", saleValue=" + saleValue + '}';
    }
}
