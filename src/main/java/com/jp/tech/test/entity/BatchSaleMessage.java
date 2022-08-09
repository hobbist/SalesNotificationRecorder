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

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    @Override
    public String toString() {
        return "BatchSaleMessage{" +
                "batchSize=" + batchSize +
                '}';
    }
}
