package com.jp.tech.test.parser;

import com.jp.tech.test.entity.AbstractSaleMessage;
import com.jp.tech.test.entity.AdjustmentSaleMessage;
import com.jp.tech.test.entity.BatchSaleMessage;
import com.jp.tech.test.exceptions.ParserException;

/**
 * Assumption - the valid message will have Singular form of producttype
 * This parser Parses incoming plain text messages into one of the valid business objects
 */
public class SalesMessageParser implements MessageParser<String,AbstractSaleMessage> {
    @Override
    public AbstractSaleMessage parse(String rawMessage) throws ParserException {
        String singleSaleMessageFormat = "^((?<product>\\w+)\\s)at(\\s(?<value>\\d+))p$";
        String batchSaleMessageFormat = "^((?<occurance>\\d+)) sales of (?<product>\\w+) at (?<value>\\d+)p each$";
        String adjustmentSaleMessageFormat="^(?<operation>\\w+) (?<value>\\d+)p (?<product>\\w+)$";
        if(rawMessage.matches(singleSaleMessageFormat)){
            String[] tokens=rawMessage.split("at");
            return new BatchSaleMessage(tokens[0].trim().toLowerCase(),Double.valueOf(tokens[1].trim().replace("p","")));
        }
        else if(rawMessage.matches(batchSaleMessageFormat)){
            String[] tokens=rawMessage.split("at");
            String[] batch=tokens[0].split("sales of");
            BatchSaleMessage message=new BatchSaleMessage(batch[1].trim().toLowerCase(),Double.valueOf(tokens[1].replaceAll("p each","").trim()));
            message.setBatchSize(Integer.valueOf(batch[0].trim()));
            return message;

        } else if(rawMessage.matches(adjustmentSaleMessageFormat)){
            String[] tokens=rawMessage.split(" ");
            return new AdjustmentSaleMessage(tokens[2].toLowerCase(),Double.valueOf(tokens[1].replaceAll("p","")),tokens[0]);
        }
        else throw new ParserException("rawMessage given is not parsable.Please consider sending correct message.This message will not be recorded-"+rawMessage);
    }
}
