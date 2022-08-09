package com.jp.tech.test.entity;

public class AbstractSaleMessage implements SaleMessage {
private String productType;
private Double saleValue; // this is in assuming the values will be accumulated to large

    public AbstractSaleMessage(String productType, Double saleValue) {
        this.productType = productType;
        this.saleValue = saleValue;
    }
}
