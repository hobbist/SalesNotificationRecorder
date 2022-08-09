package com.jp.tech.test.entity;

public class AdjustmentSaleMessage extends AbstractSaleMessage {
    private Operation operation;
    public AdjustmentSaleMessage(String productType, Double saleValue) {
        super(productType, saleValue);
    }
    public AdjustmentSaleMessage(String productType, Double saleValue,Operation operation) {
        super(productType, saleValue);
        this.operation=operation;
    }


    private class Operation{
    private String operationName;

    private Operation(String operationName) {
        this.operationName = operationName;
    }
}

}
