package com.jp.tech.test.db;

import com.jp.tech.test.entity.RecordedSale;

import java.util.Map;

public abstract class SaleRecordDataAccessObject {
    public static SaleRecordDataAccessObject getInstance(String type){
        return InMemoryDataAccessObject.getInstance();
    }
    public abstract RecordedSale getRecordByProductType(String productType);
    public abstract Boolean updateRecordByProductType(String productType, RecordedSale sales);
    public abstract Integer getMessageCount();
    public abstract Integer addMessageCountToDB(Integer count);
    public abstract Map<String, RecordedSale> getAllSaleRecords();
}
