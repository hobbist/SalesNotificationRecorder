package com.jp.tech.test.processor;

import com.jp.tech.test.entity.BatchSaleMessage;
import com.jp.tech.test.entity.RecordedSale;
import com.jp.tech.test.exceptions.ProcessorException;

/**
 * Batch Sale processor to calculate and process a batch with any size
 */
public class SaleProcessor extends AbstractProcessor<BatchSaleMessage> {
    private static SaleProcessor instance=null;
    private SaleProcessor(){
        super();
    }

    public static Processor getInstance() {
        if(instance==null) instance=new SaleProcessor();
        return instance;
    }

    public static LogProcessor<BatchSaleMessage> getLogInstance() {
        if(instance==null) instance=new SaleProcessor();
        return instance;
    }

    @Override
    public Boolean process(BatchSaleMessage message) throws ProcessorException {
      RecordedSale s = dao.getRecordByProductType(message.getProductType());
      s.addSaleRecord(message);
      s.setSaleValue(s.getSaleValue()+message.getBatchSize()*message.getSaleValue());
      dao.updateRecordByProductType(s.getProductType(),s);
      this.updateSaleCount();
      return true;
    }
}
