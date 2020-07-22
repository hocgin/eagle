package in.hocg.eagle.modules.mms.message;

import in.hocg.eagle.basic.constant.message.GroupConstant;
import in.hocg.eagle.basic.constant.message.TopicConstant;
import in.hocg.eagle.modules.mms.message.body.TestBody2;
import in.hocg.eagle.utils.string.JsonUtils;
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
@RocketMQMessageListener(topic = TopicConstant.Fields.TEST_TOPIC2,
    consumerGroup = GroupConstant.Fields.TEST_GROUP2)
public class NotifyMessageListener2 implements RocketMQListener<TestBody2> {

    @Override
    public void onMessage(TestBody2 message) {
        log.info("Message2: {}", JsonUtils.toJSONString(message));
    }
}
