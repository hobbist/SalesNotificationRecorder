package com.jp.tech.test.entity;

import java.util.HashSet;
import java.util.Set;

public class RecordedSale extends AbstractSaleMessage {
 private Set<AbstractSaleMessage> saleRecords;
 private Set<AdjustmentSaleMessage> adjustmentRecords;
 private Double totalValue=0d;

    public RecordedSale(String productType, Double saleValue) {
        super(productType, saleValue);
        this.saleRecords=new HashSet<>();
        this.adjustmentRecords=new HashSet<>();
    }

    @Override
    public Double getSaleValue() {
        return this.totalValue;
    }

    public void addSaleRecord(AbstractSaleMessage saleRecord) {
        this.saleRecords.add(saleRecord);
    }

    public Set<AbstractSaleMessage> getSaleRecords(){
        return saleRecords;
    }

    public void addAdjustmentRecords(AdjustmentSaleMessage adjustmentRecords) {
        this.adjustmentRecords.add(adjustmentRecords);
    }

    public Set<AdjustmentSaleMessage> getAdjustmentRecords() {
        return adjustmentRecords;
    }


}
