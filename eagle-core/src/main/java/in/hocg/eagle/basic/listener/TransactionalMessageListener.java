package in.hocg.eagle.basic.listener;

import in.hocg.eagle.basic.message.MessageFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Created by hocgin on 2020/7/20.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Component
public class TransactionalMessageListener {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterCommit(ApplicationEvent event) {
        MessageFactory.transactional().publish();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void rollback(ApplicationEvent event) {
        MessageFactory.transactional().clear();
    }

}

