package com.jp.tech.test.recorder;

import com.jp.tech.test.entity.SaleMessage;
import com.jp.tech.test.exceptions.RecorderException;

/*
   Class Acts as a template to run the steps to record given message
   Also it logs the statuses as per log processing
 */
public interface Recorder<T,K extends SaleMessage> {
    default void recordIncomingMessage(T message){
        K salesMessage= null;
        try {
            if (doReceive()) {
                salesMessage = parseAndValidate(message);
                logMessage(salesMessage);
                processMessage(salesMessage);
                displayStatusAfterMessage(salesMessage);
                if(!doReceive()){
                    System.out.println("System has received over its threshold stopping app now");
                    System.exit(0);
                }
            }
        } catch (RecorderException e) {
            System.out.println(e.getMessage());
        }
    }

    void displayStatusAfterMessage(K message);
    K parseAndValidate(T t) throws RecorderException;
    void logMessage(K message) throws RecorderException;
    void processMessage(K message) throws RecorderException;
    Boolean doReceive();
}
