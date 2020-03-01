package in.hocg.eagle.modules.base.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.mapstruct.DataDictItemMapping;
import in.hocg.eagle.mapstruct.qo.datadict.DataDictPostQo;
import in.hocg.eagle.modules.base.entity.DataDictItem;
import in.hocg.eagle.modules.base.mapper.DataDictItemMapper;
import in.hocg.eagle.modules.base.service.DataDictItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * [基础模块] 数据字典项表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-14
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class DataDictItemServiceImpl extends AbstractServiceImpl<DataDictItemMapper, DataDictItem> implements DataDictItemService {
    
    private final DataDictItemMapping mapping;
    
    @Override
    public void insertOne(Long dictId, DataDictPostQo.DataDictItemPostQo qo) {
        DataDictItem entity = mapping.asDataDictItem(qo);
        entity.setDictId(dictId);
        entity.setCreator(qo.getUserId());
        entity.setCreatedAt(qo.getCreatedAt());
        insert(entity);
    }
    
    @Override
    public void deleteByDictId(Long dictId) {
        baseMapper.delete(lambdaUpdate().eq(DataDictItem::getDictId, dictId));
    }
    
    @Override
    public List<DataDictItem> selectListByDictId(Long dictId) {
        return baseMapper.selectList(lambdaQuery().eq(DataDictItem::getDictId, dictId));
    }
    
    private void insert(DataDictItem entity) {
        final String code = entity.getCode();
        if (hasCode(code)) {
            throw ServiceException.wrap("新增失败,字典项码已经存在");
        }
        baseMapper.insert(entity);
    }
    
    private boolean hasCode(String code) {
        return lambdaQuery().eq(DataDictItem::getCode, code).count() > 0;
    }
}
