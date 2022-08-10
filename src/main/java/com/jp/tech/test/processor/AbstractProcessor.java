package com.jp.tech.test.processor;

import com.jp.tech.test.db.SaleRecordDataAccessObject;
import com.jp.tech.test.entity.AbstractSaleMessage;
import com.jp.tech.test.exceptions.ProcessorException;

/**
 * This Abstrcact processor acts as logger as well as message processor.
 */
public abstract class AbstractProcessor<T extends AbstractSaleMessage> implements Processor<T>,LogProcessor<T> {
    private int checkPointCount;
    private int stopCount;

    protected SaleRecordDataAccessObject dao;
    public AbstractProcessor(){
        dao=SaleRecordDataAccessObject.getInstance("inMemory");
        checkPointCount=System.getProperty("checkPointCount")!=null?Integer.valueOf(System.getProperty("checkPointCount")):10;
        stopCount=System.getProperty("stopCount")!=null?Integer.valueOf(System.getProperty("stopCount")):50;
    }

    protected void updateSaleCount(){
        dao.addMessageCountToDB(1);
    }
    protected Integer getSaleCount(){
        return dao.getMessageCount();
    }


    @Override
    public void log(T message) throws ProcessorException {
        System.out.println("-------------Start of Transaction---------");
        System.out.println();
    }

    @Override
    public String diplayRecordsStatus() {
        String op="n";
        if(getSaleCount()%checkPointCount==0 ){
            //print
            System.out.println("---------Display Check Point---------------");
            System.out.println();
            dao.getAllSaleRecords().forEach((x,y)-> {
                System.out.println("ProductType - "+x);
                System.out.println("Sales records - "+y.getSaleRecords());
                System.out.println("total Sale - "+y.getSaleValue());
                System.out.println("------------------------");
            });
            System.out.println();
            op= String.valueOf(checkPointCount);
        }
        if(getSaleCount()==stopCount){
            System.out.println("---------Display Application Stop Point---------------");
            dao.getAllSaleRecords().forEach((x,y)-> {
                System.out.println("ProductType - "+x);
                System.out.println("total Sale - "+y.getSaleValue());
                System.out.println("Adjustments - "+y.getAdjustmentRecords());
                System.out.println("------------------------");
            });
            op= String.valueOf(stopCount);
        }
          System.out.println("-------------End of Transaction---------");
         return op;
    }

    @Override
    public void diplayCurrentRecordsStatus(Boolean displayCurrent) {
        if(displayCurrent) {
            dao.getAllSaleRecords().forEach((x, y) -> {
                System.out.println("------------------------");
                System.out.println("ProductType - " + x);
                System.out.println("Adjustments - " + y.getAdjustmentRecords());
                System.out.println("Sales - " + y.getSaleRecords());
                System.out.println("AggregatedValue - " + y.getSaleValue());
                System.out.println("------------------------");
            });
        }
    }

    @Override
    public void stopProcessing() {
        System.out.println();
        System.exit(1);

    }
}
