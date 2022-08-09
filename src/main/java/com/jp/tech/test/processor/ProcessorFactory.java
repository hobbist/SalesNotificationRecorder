package com.jp.tech.test.processor;

import com.jp.tech.test.entity.AbstractSaleMessage;
import com.jp.tech.test.entity.AdjustmentSaleMessage;
import com.jp.tech.test.entity.BatchSaleMessage;

public class ProcessorFactory {
    public static Processor getProcessorforMessage(AbstractSaleMessage message){
        if(message instanceof AdjustmentSaleMessage) return AdjustmentProcessor.getInstance();
        if(message instanceof BatchSaleMessage) return SaleProcessor.getInstance();
        return null;
    }

    public static LogProcessor getLogProcessorForMessage(AbstractSaleMessage message){
        if(message instanceof AdjustmentSaleMessage) return AdjustmentProcessor.getLoggerInstance();
        if(message instanceof BatchSaleMessage) return SaleProcessor.getLogInstance();
        return null;
    }
}
