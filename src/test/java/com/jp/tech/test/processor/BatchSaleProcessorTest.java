package com.jp.tech.test.processor;

import com.jp.tech.test.db.InMemoryDataAccessObject;
import com.jp.tech.test.entity.AbstractSaleMessage;
import com.jp.tech.test.entity.AdjustmentSaleMessage;
import com.jp.tech.test.entity.BatchSaleMessage;
import com.jp.tech.test.entity.RecordedSale;
import com.jp.tech.test.exceptions.ProcessorException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class BatchSaleProcessorTest {
    private static Processor<BatchSaleMessage> processor;
    private static Processor<AbstractSaleMessage> adjustmentProcessor;
    private static InMemoryDataAccessObject db;

    @BeforeClass
    public static void createTestGround(){
        processor=SaleProcessor.getInstance();
        adjustmentProcessor=AdjustmentProcessor.getInstance();
        db=InMemoryDataAccessObject.getInstance();
    }

    @Before
    @After
    public void clearDataIfAny() throws NoSuchFieldException {
        Field map=InMemoryDataAccessObject.class.getDeclaredField("productSalesDB");
        Field count=InMemoryDataAccessObject.class.getDeclaredField("messageCount");
        map.setAccessible(true);
        count.setAccessible(true);
        try {
            map.set(db,new HashMap<>());
            count.set(db,0);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addSingleProductSaleTest(){
        try {
            BatchSaleMessage message=new BatchSaleMessage("orange",10d);
            processor.process(message);
            message=new BatchSaleMessage("orange",10d);
            processor.process(message);
            message=new BatchSaleMessage("orange",10d);
            processor.process(message);
            message=new BatchSaleMessage("orange",10d);
            processor.process(message);
            ((LogProcessor)processor).diplayCurrentRecordsStatus(true);
            assertTrue(db.getRecordByProductType("orange").getSaleValue()==40.0);
            assertTrue(db.getMessageCount()==4);
        } catch (ProcessorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addBatchProductSaleTest(){
        try {
            BatchSaleMessage message=new BatchSaleMessage("orange",10d,4);
            processor.process(message);
            message=new BatchSaleMessage("orange",10d);
            processor.process(message);
            message=new BatchSaleMessage("orange",10d);
            processor.process(message);
            message=new BatchSaleMessage("orange",10d);
            processor.process(message);
            assertTrue(db.getRecordByProductType("orange").getSaleValue()==70.0);
            assertTrue(db.getMessageCount()==4);
            ((LogProcessor)processor).diplayCurrentRecordsStatus(true);
        } catch (ProcessorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addBatchExistingProductSaleTest(){
        try {
            RecordedSale sale=new RecordedSale("orange",40d);
            sale.addSaleRecord(new BatchSaleMessage("orange",20d,2));
            db.updateRecordByProductType("orange",sale);
            BatchSaleMessage message=new BatchSaleMessage("orange",10d,4);
            processor.process(message);
            assertTrue(db.getRecordByProductType("orange").getSaleValue()==80.0);
            ((LogProcessor)processor).diplayCurrentRecordsStatus(true);
        } catch (ProcessorException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void addBatchExistingProductSaleAdjustmentTest(){
        try {
            BatchSaleMessage message=new BatchSaleMessage("orange",10d,4);
            processor.process(message);
            AdjustmentSaleMessage adMessage=new AdjustmentSaleMessage("orange",50d,"add");
            adjustmentProcessor.process(adMessage);
            assertTrue(db.getRecordByProductType("orange").getSaleValue()==240.0);
            ((LogProcessor)processor).diplayCurrentRecordsStatus(true);
        } catch (ProcessorException e) {
            e.printStackTrace();
        }
    }
}
