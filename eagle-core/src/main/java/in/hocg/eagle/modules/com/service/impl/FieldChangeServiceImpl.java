package in.hocg.eagle.modules.com.service.impl;

import in.hocg.eagle.modules.com.entity.FieldChange;
import in.hocg.eagle.modules.com.mapper.FieldChangeMapper;
import in.hocg.eagle.modules.com.service.FieldChangeService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * [基础模块] 业务日志-字段变更记录表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-11
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FieldChangeServiceImpl extends AbstractServiceImpl<FieldChangeMapper, FieldChange> implements FieldChangeService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void validInsertOrUpdateByChangeLogId(Long changeLogId, List<FieldChange> entities) {
        entities.forEach(this::validInsertOrUpdate);
    }
}
