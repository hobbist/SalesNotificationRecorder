package com.jp.tech.test.db;

import com.jp.tech.test.entity.RecordedSale;

import java.util.HashMap;
import java.util.Map;

public class InMemoryDataAccessObject extends SaleRecordDataAccessObject {
    private static InMemoryDataAccessObject instance;
    private Map<String, RecordedSale> productSalesDB= new HashMap<>();
    private Integer messageCount=0;
    private InMemoryDataAccessObject(){
    }

    public static InMemoryDataAccessObject getInstance(){
        if(instance==null) {
            instance=new InMemoryDataAccessObject();
        }
        return instance;
    }

    public RecordedSale getRecordByProductType(String productType){
       return productSalesDB.entrySet().stream().
                filter(x->x.getKey().equals(productType)).
                map(Map.Entry::getValue)
                .findFirst().orElse(new RecordedSale(productType,0d));
    }

    public Boolean updateRecordByProductType(String productType,RecordedSale sales){
       return productSalesDB.put(productType,sales)!=null?true:false;
    }

    public Integer getMessageCount(){
        return this.messageCount;
    }

    public Integer addMessageCountToDB(Integer count){
        this.messageCount=this.messageCount+count;
        return this.messageCount;
    }

    @Override
    public Map<String, RecordedSale> getAllSaleRecords() {
        return new HashMap<>(productSalesDB);
    }
}
