package com.jp.tech.test.db;

import com.jp.tech.test.entity.RecordedSale;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NotificationDB {
    private Map<String, RecordedSale> productSalesDB= new HashMap<>();

    public RecordedSale getRecordByProductType(String productType){
        productSalesDB.entrySet().stream().
                filter(x->x.getKey().equals(productType)).
                map(Map.Entry::getValue)
                .findFirst().orElse(new RecordedSale(productType,0d));
    }

    public Boolean updateRecordByProductType(String productType,RecordedSale sales){
       return productSalesDB.put(productType,sales)!=null?true:false;
    }
}
