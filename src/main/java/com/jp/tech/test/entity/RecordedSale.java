package com.jp.tech.test.entity;

import java.util.HashSet;
import java.util.Set;

public class RecordedSale extends AbstractSaleMessage {
 private Set<SaleMessage> saleRecords;
 private Set<SaleMessage> adjustmentRecords;

    public RecordedSale(String productType, Double saleValue) {
        super(productType, saleValue);
        this.saleRecords=new HashSet<>();
        this.adjustmentRecords=new HashSet<>();
    }
}
