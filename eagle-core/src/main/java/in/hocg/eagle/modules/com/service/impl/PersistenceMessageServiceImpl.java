package in.hocg.eagle.modules.com.service.impl;

import in.hocg.eagle.basic.constant.datadict.PersistenceMessagePublished;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractServiceImpl;
import in.hocg.eagle.basic.message.core.transactional.TransactionalMessage;
import in.hocg.eagle.basic.message.core.transactional.TransactionalMessageRepository;
import in.hocg.eagle.modules.com.entity.PersistenceMessage;
import in.hocg.eagle.modules.com.mapper.PersistenceMessageMapper;
import in.hocg.eagle.modules.com.mapstruct.PersistenceMessageMapping;
import in.hocg.eagle.modules.com.service.PersistenceMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * [基础模块] 持久化消息表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-07-20
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class PersistenceMessageServiceImpl extends AbstractServiceImpl<PersistenceMessageMapper, PersistenceMessage>
    implements PersistenceMessageService, TransactionalMessageRepository {
    private final PersistenceMessageMapping mapping;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(TransactionalMessage message) {
        final LocalDateTime now = LocalDateTime.now();
        PersistenceMessage entity = mapping.asPersistenceMessage(message);
        entity.setPublished(PersistenceMessagePublished.Prepare.getCode());
        entity.setCreatedAt(now);
        return save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(List<TransactionalMessage> messages) {
        final LocalDateTime now = LocalDateTime.now();
        final List<PersistenceMessage> entities = messages.parallelStream()
            .map(message -> mapping.asPersistenceMessage(message)
                .setPublished(PersistenceMessagePublished.Prepare.getCode())
                .setCreatedAt(now))
            .collect(Collectors.toList());
        return saveBatch(entities);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean complete(Long messageId) {
        final Integer completeCode = PersistenceMessagePublished.Complete.getCode();
        final PersistenceMessage update = new PersistenceMessage()
            .setPublished(completeCode)
            .setUpdatedAt(LocalDateTime.now());
        return lambdaUpdate().eq(PersistenceMessage::getId, messageId)
            .ne(PersistenceMessage::getPublished, completeCode)
            .update(update);
    }

}
