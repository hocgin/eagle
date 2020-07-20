package in.hocg.eagle.modules.com.mapstruct;

import in.hocg.eagle.basic.message.core.transactional.TransactionalMessage;
import in.hocg.eagle.modules.com.entity.PersistenceMessage;
import org.mapstruct.Mapper;

/**
 * Created by hocgin on 2020/7/20.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface PersistenceMessageMapping {

    default PersistenceMessage asPersistenceMessage(TransactionalMessage message) {
        return new PersistenceMessage()
            .setDestination(message.getDestination())
            .setGroupSn(message.getGroupSn())
            .setPayload(message.getPayload())
            .setPreparedAt(message.getPreparedAt());
    }
}
