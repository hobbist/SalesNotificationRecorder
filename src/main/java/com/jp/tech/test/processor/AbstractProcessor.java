package com.jp.tech.test.processor;

import com.jp.tech.test.db.SaleRecordDataAccessObject;
import com.jp.tech.test.entity.AbstractSaleMessage;
import com.jp.tech.test.exceptions.ProcessorException;

public abstract class AbstractProcessor<T extends AbstractSaleMessage> implements Processor<T>,LogProcessor<T> {
    protected SaleRecordDataAccessObject dao;
    public AbstractProcessor(){
        dao=SaleRecordDataAccessObject.getInstance("inMemory");
    }

    protected void updateSaleCount(){
        dao.addMessageCountToDB(1);
    }
    protected Integer getSaleCount(){
        return dao.getMessageCount();
    }


    @Override
    public void log(T message) throws ProcessorException {
        System.out.println("Logging the message"+ message);
    }

    @Override
    public void diplayRecordsStatus() {
        if(getSaleCount()%10==0){
            //print
            dao.getAllSaleRecords().forEach((x,y)-> {
                System.out.println("************************");
                System.out.println("ProductType - "+x);
                System.out.println("total Sale - "+y.getSaleValue());
                System.out.println("************************");
            });
        }
        if(getSaleCount()==50){
            dao.getAllSaleRecords().forEach((x,y)-> {
                System.out.println("************************");
                System.out.println("ProductType - "+x);
                System.out.println("Adjustments - "+y.getAdjustmentRecords());
                System.out.println("************************");
            });
            stopProcessing();
        }
    }

    @Override
    public void stopProcessing() {
        System.out.println();
        System.exit(1);

    }
}
