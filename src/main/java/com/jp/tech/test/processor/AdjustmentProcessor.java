package com.jp.tech.test.processor;

import com.jp.tech.test.entity.AdjustmentSaleMessage;
import com.jp.tech.test.entity.BatchSaleMessage;
import com.jp.tech.test.entity.RecordedSale;
import com.jp.tech.test.exceptions.ProcessorException;


public class AdjustmentProcessor extends AbstractProcessor<AdjustmentSaleMessage> {
    private static AdjustmentProcessor instance;
    private AdjustmentProcessor(){
    }

    public static Processor getInstance(){
        if(instance==null) {instance=new AdjustmentProcessor();}
        return instance;
    }

    public static LogProcessor<AdjustmentSaleMessage> getLoggerInstance(){
        if(instance==null) {instance=new AdjustmentProcessor();}
        return instance;
    }

    @Override
    public Boolean process(AdjustmentSaleMessage message) throws ProcessorException {
        RecordedSale s=dao.getRecordByProductType(message.getProductType());
        s.addAdjustmentRecords(message);
        if(s.getSaleRecords().size()!=0) {
            s.getSaleRecords().stream().map(x -> {
                if (x instanceof BatchSaleMessage) {
                    message.getOperation().apply(x, message.getSaleValue());
                    return x;
                }
                return x;
            });
            dao.updateRecordByProductType(s.getProductType(),s);
        }else{
            System.out.println("No Sales records to be adjusted for given product type");
        }
        this.updateSaleCount();
        return true;
    }
}
