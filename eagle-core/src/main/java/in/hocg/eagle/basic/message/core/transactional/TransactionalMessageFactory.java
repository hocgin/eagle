package in.hocg.eagle.basic.message.core.transactional;

import cn.hutool.core.util.IdUtil;
import in.hocg.eagle.basic.ext.web.SpringContext;
import in.hocg.eagle.basic.message.core.MessageFactory;

import java.util.List;

/**
 * Created by hocgin on 2020/7/20.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class TransactionalMessageFactory implements MessageFactory {
    public static final TransactionalMessageFactory ME = new TransactionalMessageFactory();

    private TransactionalMessageFactory() {
    }

    public void prepare(TransactionalMessage message) {
        TransactionalMessageContext.add(message);
    }

    public void publish() {
        final TransactionalMessageRepository repository = SpringContext.getBean(TransactionalMessageRepository.class);
        final String messageGroupSn = getMessageGroupSn();
        final List<TransactionalMessage> messages = TransactionalMessageContext.getAndClear();
        for (TransactionalMessage message : messages) {
            message.setGroupSn(messageGroupSn);
            repository.save(message);
        }
    }

    public void clear() {
        TransactionalMessageContext.clear();
    }

    private String getMessageGroupSn() {
        return IdUtil.fastSimpleUUID();
    }
}
