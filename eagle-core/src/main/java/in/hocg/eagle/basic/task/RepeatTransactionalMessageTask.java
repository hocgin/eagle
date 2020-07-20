package in.hocg.eagle.basic.task;

import in.hocg.eagle.basic.ext.web.SpringContext;
import in.hocg.eagle.basic.message.MessageFactory;
import in.hocg.eagle.basic.message.core.transactional.TransactionalMessageRepository;
import in.hocg.eagle.modules.com.entity.PersistenceMessage;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by hocgin on 2020/7/20.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Component
public class RepeatTransactionalMessageTask implements InitializingBean {

    private TransactionalMessageRepository repository;

    @Scheduled(cron = "0/5 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void listener() {
        final LocalDateTime after = LocalDateTime.now().plusMinutes(0);
        int limit = 100;
        final List<PersistenceMessage> messages = repository.selectListByUnCompleteAndAfter(after, limit);

        for (PersistenceMessage message : messages) {
            ((RepeatTransactionalMessageTask) AopContext.currentProxy()).asyncSend(message);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void syncSend(PersistenceMessage message) {
        repository.complete(message.getId());
        SendResult sendResult = MessageFactory.normal().syncSend(message);
        if (!SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            throw new RuntimeException("发送失败");
        }
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    public void asyncSend(PersistenceMessage message) {
        this.syncSend(message);
    }

    @Override
    public void afterPropertiesSet() {
        repository = SpringContext.getBean(TransactionalMessageRepository.class);
    }
}
