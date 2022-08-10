package com.jp.tech.test.processor;

import com.jp.tech.test.db.InMemoryDataAccessObject;
import com.jp.tech.test.entity.AdjustmentSaleMessage;
import com.jp.tech.test.entity.BatchSaleMessage;
import com.jp.tech.test.entity.RecordedSale;
import com.jp.tech.test.exceptions.ProcessorException;
import static  org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;

public class AdjustmentProcessorTest {
    private static Processor<AdjustmentSaleMessage> processor;
    private static InMemoryDataAccessObject db;

    @BeforeClass
    public static void createTestGround(){
        processor=AdjustmentProcessor.getInstance();
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
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void runAdjustmentsForNonExistingProduct(){
        AdjustmentSaleMessage message=new AdjustmentSaleMessage("apple",10d,"add");
        try {
            assertFalse(processor.process(message));
            ((LogProcessor)processor).diplayCurrentRecordsStatus(true);
        } catch (ProcessorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void runAdjustmentsForExistingProduct(){
        //creating dummy instance of database to set up recordsale for test product
        RecordedSale sale=new RecordedSale("orange",40d);
        sale.addSaleRecord(new BatchSaleMessage("orange",20d,2));
        db.updateRecordByProductType("orange",sale);
        AdjustmentSaleMessage message=new AdjustmentSaleMessage("orange",10d,"add");
        try {
            assertTrue(processor.process(message));
            message=new AdjustmentSaleMessage("orange",10d,"substract");
            assertTrue(processor.process(message));
            message=new AdjustmentSaleMessage("orange",10d,"multiply");
            assertTrue(processor.process(message));
            ((LogProcessor)processor).diplayCurrentRecordsStatus(true);
            assertTrue(db.getAllSaleRecords().get("orange").getSaleValue()==400.0);
        } catch (ProcessorException e) {
            e.printStackTrace();
        }
    }
}
