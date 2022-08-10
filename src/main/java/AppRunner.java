import com.jp.tech.test.entity.AbstractSaleMessage;
import com.jp.tech.test.recorder.Recorder;
import com.jp.tech.test.recorder.SaleRecorder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * Assumption - Stream of messages is replicated as a ArrayStream
 * it uses Recorder
 */
public class AppRunner {
    public static void main(String[] args){
        InputStream is = AppRunner.class.getClassLoader().getResourceAsStream("app.properties");
        Properties appProps=new Properties();
        try {
            appProps.load(is);
            appProps.stringPropertyNames().stream().forEach(p-> System.setProperty(p,appProps.getProperty(p)));
            List<String> inputs=DataGenerator.getData();
            run(args, appProps, inputs);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void run(String[] args, Properties appProps, List<String> inputs) {
        if(args.length==1){
            Recorder<String,AbstractSaleMessage> recorder=new SaleRecorder();
            //Adding shutDown Hook for graceful shutdown
            final Thread mainThread = Thread.currentThread();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    if(!recorder.doReceive()){
                     System.out.println("closing System Now");
                    }
                }
            });
        //console option
        if(args[0].equalsIgnoreCase("console")) {
            while(true){
                Scanner in = new Scanner(System.in);
                recorder.recordIncomingMessage(in.nextLine());
            }
        //running on prerecorded messages
        }else{
            for(String i:inputs) {
                recorder.recordIncomingMessage(i);
            }
        }
        }else{
         System.out.println("Usage - need to provide either console/dummy to run the system");
            System.out.println("System properties set as -");
         System.out.println(appProps);
        }
    }
}
