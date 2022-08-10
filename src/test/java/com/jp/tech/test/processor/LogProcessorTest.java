package com.jp.tech.test.processor;

import com.jp.tech.test.db.InMemoryDataAccessObject;
import com.jp.tech.test.entity.AbstractSaleMessage;
import com.jp.tech.test.entity.AdjustmentSaleMessage;
import com.jp.tech.test.entity.BatchSaleMessage;
import com.jp.tech.test.exceptions.ProcessorException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class LogProcessorTest {
    private static Processor<BatchSaleMessage> processor;
    private static Processor<AbstractSaleMessage> adjustmentProcessor;
    private static InMemoryDataAccessObject db;

    @BeforeClass
    public static void createTestGround(){
        System.setProperty("checkPointCount","5");
        System.setProperty("stopCount","6");
        processor=SaleProcessor.getInstance();
        adjustmentProcessor=AdjustmentProcessor.getInstance();
        db=InMemoryDataAccessObject.getInstance();
    }

    @Before
    @After
    public void clearDataIfAny() throws NoSuchFieldException {
        Field map=InMemoryDataAccessObject.class.getDeclaredField("productSalesDB");
        Field count=InMemoryDataAccessObject.class.getDeclaredField("messageCount");
        Field checkPointCount=SaleProcessor.class.getSuperclass().getDeclaredField("checkPointCount");
        Field stopCount=SaleProcessor.class.getSuperclass().getDeclaredField("stopCount");
        map.setAccessible(true);
        count.setAccessible(true);
        stopCount.setAccessible(true);
        checkPointCount.setAccessible(true);
        try {
            map.set(db,new HashMap<>());
            count.set(db,0);
            checkPointCount.set(processor,5);
            checkPointCount.set(adjustmentProcessor,5);
            stopCount.set(processor,6);
            stopCount.set(adjustmentProcessor,6);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckPointDisplayCall(){
        try {
            BatchSaleMessage message = new BatchSaleMessage("orange", 10d);
            processor.process(message);
            message = new BatchSaleMessage("orange", 10d);
            processor.process(message);
            message = new BatchSaleMessage("orange", 10d);
            processor.process(message);
            message = new BatchSaleMessage("orange", 10d);
            processor.process(message);
            assertTrue(((LogProcessor)processor).diplayRecordsStatus().equalsIgnoreCase("n"));
            message = new BatchSaleMessage("apple", 10d);
            processor.process(message);
            assertTrue(((LogProcessor)processor).diplayRecordsStatus().equalsIgnoreCase("5"));
        }catch (ProcessorException e ){
            e.printStackTrace();
        }

    }

    @Test
    public void testStopDisplayCall(){
        try {
            BatchSaleMessage message = new BatchSaleMessage("orange", 10d);
            processor.process(message);
            message = new BatchSaleMessage("orange", 10d);
            processor.process(message);
            message = new BatchSaleMessage("orange", 10d);
            processor.process(message);
            message = new BatchSaleMessage("orange", 10d);
            processor.process(message);
            assertTrue(((LogProcessor)processor).diplayRecordsStatus().equalsIgnoreCase("n"));
            message = new BatchSaleMessage("apple", 10d);
            processor.process(message);
            assertTrue(((LogProcessor)processor).diplayRecordsStatus().equalsIgnoreCase("5"));
            AdjustmentSaleMessage adMessage=new AdjustmentSaleMessage("orange",10d,"add");
            adjustmentProcessor.process(adMessage);
            assertTrue(((LogProcessor)processor).diplayRecordsStatus().equalsIgnoreCase("6"));
        }catch (ProcessorException e ){
            e.printStackTrace();
        }

    }
}
