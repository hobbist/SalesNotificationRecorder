package com.jp.tech.test.entity;

public class AdjustmentSaleMessage extends AbstractSaleMessage {
    private Operation operation;
    public AdjustmentSaleMessage(String productType, Double saleValue,String operation) {
        super(productType, saleValue);
        this.setOperation(operation);
    }
    public Operation getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = new Operation(operation);
    }

    @Override
    public String toString() {
        return "AdjustmentSaleMessage{" +
                "operation=" + operation +
                '}';
    }

    public class Operation{
        private String operationName;
        Operation(String operationName) {
            this.operationName = operationName;
        }
        public String getOperationName() {
            return operationName;
        }

        public void setOperationName(String operationName) {
            this.operationName = operationName;
        }

        public void apply(AbstractSaleMessage record,Double value){
            if(this.operationName.equalsIgnoreCase("add")){
                record.setSaleValue(record.getSaleValue() + value);
            }
            if(this.operationName.equalsIgnoreCase("substract")){
                record.setSaleValue(record.getSaleValue() - value);
            }
            if(this.operationName.equalsIgnoreCase("multiply")){
                record.setSaleValue(record.getSaleValue() * value);
            }
        }

    }
}

