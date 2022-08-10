package com.jp.tech.test.processor;

import com.jp.tech.test.entity.AdjustmentSaleMessage;
import com.jp.tech.test.entity.BatchSaleMessage;
import com.jp.tech.test.entity.RecordedSale;
import com.jp.tech.test.exceptions.ProcessorException;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Processor for Adjustment Records
 */
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
            AtomicReference<Double> i= new AtomicReference<>(0d);
            s.setSaleRecords(s.getSaleRecords().stream().map(x -> {
                    if(x instanceof BatchSaleMessage) {
                        message.getOperation().apply(x, message.getSaleValue());
                        i.set(i.get() + x.getSaleValue()*((BatchSaleMessage) x).getBatchSize());
                        return x;
                    }
                    return x;
            }).collect(Collectors.toSet()));
            s.setSaleValue(i.get());
            dao.updateRecordByProductType(s.getProductType(),s);
        }else{
            System.out.println("No Sales records to be adjusted for given product type "+message.getProductType());
            System.out.println(message);
            this.updateSaleCount();
            return false;
        }
        this.updateSaleCount();
        return true;
    }
}
