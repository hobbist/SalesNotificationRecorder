package com.jp.tech.test.parser;


import com.jp.tech.test.entity.AbstractSaleMessage;
import com.jp.tech.test.entity.AdjustmentSaleMessage;
import com.jp.tech.test.entity.BatchSaleMessage;
import com.jp.tech.test.exceptions.ParserException;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
/*
Message Type 1 – apple at 10p
o Message Type 2 – 20 sales of apples at 10p each.
o Message Type 3 – Operations can be add, subtract, or multiply - Add 20p apples
 */
public class SalesMessageParserTest {
    private static MessageParser<String,AbstractSaleMessage> parser;

    @BeforeClass
    public static void createTestGround(){
        parser=new SalesMessageParser();
    }

  @Test(expected = ParserException.class)
  public void testsingleSaleMessageParsing() throws ParserException {
      String correctTestMessage="apple at 10p";
      String inCorrectTestMessage="apple at 10p each";
      AbstractSaleMessage saleMessage=parser.parse(correctTestMessage);
      assertTrue(saleMessage instanceof BatchSaleMessage);
      assertTrue(saleMessage.getProductType().equals("apple"));
      assertTrue(saleMessage.getSaleValue().equals(10d));
      assertFalse(parser.parse(inCorrectTestMessage) instanceof AbstractSaleMessage);
  }

    @Test(expected = ParserException.class)
    public void testBatchSaleMessageParsing() throws ParserException {
        String correctTestMessage="20 sales of apple at 10p each";
        String inCorrectTestMessage="20 sales of apple at 10p";
        AbstractSaleMessage saleMessage=parser.parse(correctTestMessage);
        assertTrue(saleMessage instanceof BatchSaleMessage);
        assertTrue(saleMessage.getProductType().equals("apple"));
        assertTrue(saleMessage.getSaleValue().equals(10d));
        assertTrue(((BatchSaleMessage)saleMessage).getBatchSize()==20);
        assertFalse(parser.parse(inCorrectTestMessage) instanceof AbstractSaleMessage);
    }

    @Test(expected = ParserException.class)
    public void testAdjustmentMessageParsing() throws ParserException {
        String correctTestMessage="Add 20p apple";
        String inCorrectTestMessage="do Add 20p apple";
        AbstractSaleMessage saleMessage=parser.parse(correctTestMessage);
        assertTrue(saleMessage instanceof AdjustmentSaleMessage);
        assertTrue(saleMessage.getProductType().equals("apple"));
        assertTrue(saleMessage.getSaleValue().equals(20d));
        AdjustmentSaleMessage.Operation op=((AdjustmentSaleMessage)saleMessage).getOperation();
        assertTrue(op.getOperationName().equals("Add"));
        assertFalse(parser.parse(inCorrectTestMessage) instanceof AbstractSaleMessage);
    }
}
