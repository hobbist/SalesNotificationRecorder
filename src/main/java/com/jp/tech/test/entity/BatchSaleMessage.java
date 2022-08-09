package com.jp.tech.test.entity;

public class BatchSaleMessage extends AbstractSaleMessage {
    int batchSize;
    public BatchSaleMessage(String productType, Double saleValue) {
        super(productType, saleValue);
    }
    public BatchSaleMessage(String productType, Double saleValue,int batchSize) {
        super(productType, saleValue);
        this.batchSize=batchSize;
    }
}
