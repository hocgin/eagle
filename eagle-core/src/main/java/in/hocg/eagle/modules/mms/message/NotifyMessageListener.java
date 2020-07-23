package in.hocg.eagle.modules.mms.message;

import in.hocg.eagle.basic.constant.message.GroupConstant;
import in.hocg.eagle.basic.constant.message.TopicConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * Created by hocgin on 2020/7/22.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */

@Slf4j
@Component
@RocketMQMessageListener(topic = TopicConstant.Fields.TEST_TOPIC, consumerGroup = GroupConstant.Fields.TEST_GROUP)
public class NotifyMessageListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("Message: {}", message);
    }
}
