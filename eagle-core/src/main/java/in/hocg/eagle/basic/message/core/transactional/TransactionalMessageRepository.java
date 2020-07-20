package in.hocg.eagle.basic.message.core.transactional;

import java.util.List;

/**
 * Created by hocgin on 2020/7/20.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface TransactionalMessageRepository {

    boolean save(TransactionalMessage message);

    boolean save(List<TransactionalMessage> messages);

    boolean complete(Long messageId);
}
