package in.hocg.eagle.basic.message.core.transactional;

import in.hocg.eagle.modules.com.entity.PersistenceMessage;

import java.time.LocalDateTime;
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

    List<PersistenceMessage> selectListByUnCompleteAndAfter(LocalDateTime startAt, Integer limit);

}
