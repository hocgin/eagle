package in.hocg.eagle.statemachine;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;

/**
 * Created by hocgin on 2020/2/27.
 * email: hocgin@gmail.com
 * 状态机
 * 参考: https://www.jianshu.com/p/9ee887e045dd
 *
 * @author hocgin
 */
public class StatemachineMain {
    
    private static StateMachine<PayState, PayEvent> payStateMachine;
    
    
    public static void main(String[] args) {
        Message<PayEvent> message = MessageBuilder.withPayload(PayEvent.Pay)
                .setHeader("xx", "v")
                .setHeader("xx2", "v")
                .build();
        payStateMachine.sendEvent(message);
    }
}
