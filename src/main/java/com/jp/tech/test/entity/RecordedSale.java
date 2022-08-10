package com.jp.tech.test.entity;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class RecordedSale extends AbstractSaleMessage {
 private Set<AbstractSaleMessage> saleRecords;
 private Set<AdjustmentSaleMessage> adjustmentRecords;

    public RecordedSale(String productType, Double saleValue) {
        super(productType, saleValue);
        this.saleRecords=new LinkedHashSet<>();
        this.adjustmentRecords=new LinkedHashSet<>();
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

    public void setSaleRecords(Set<AbstractSaleMessage> saleRecords) {
        this.saleRecords = saleRecords;
    }
}
