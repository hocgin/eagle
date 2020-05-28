package in.hocg.eagle.modules.com.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.modules.com.mapstruct.FieldChangeMapping;
import in.hocg.eagle.modules.com.entity.FieldChange;
import in.hocg.eagle.modules.com.mapper.FieldChangeMapper;
import in.hocg.eagle.modules.com.pojo.vo.changelog.FieldChangeComplexVo;
import in.hocg.eagle.modules.com.service.FieldChangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    private final FieldChangeMapping mapping;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void validInsertOrUpdateByChangeLogId(Long changeLogId, List<FieldChange> entities) {
        entities.forEach(this::validInsertOrUpdate);
    }

    @Override
    public List<FieldChangeComplexVo> selectAllByChangeLogId(Long changeLogId) {
        List<FieldChange> entities = baseMapper.selectAllByChangeLogId(changeLogId);
        return entities.stream().map(this::convertComplex)
            .collect(Collectors.toList());
    }

    private FieldChangeComplexVo convertComplex(FieldChange entity) {
        return mapping.asFieldChangeComplexVo(entity);
    }
}
