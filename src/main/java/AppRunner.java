import com.jp.tech.test.entity.AbstractSaleMessage;
import com.jp.tech.test.recorder.Recorder;
import com.jp.tech.test.recorder.SaleRecorder;

import java.util.Arrays;
import java.util.List;

/**
 * Assumption - Stream of messages is replicated as a ArrayStream
 * it uses Recorder
 */
public class AppRunner {
    public static void main(String[] args){
        List<String> inputs=Arrays.asList("","","");
        Recorder<String,AbstractSaleMessage> recorder=new SaleRecorder();
        for(String i:inputs) {
            recorder.recordIncomingMessage(i);
        }
    }
}
