package in.hocg.eagle.modules.base.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.pojo.qo.IdsQo;
import in.hocg.eagle.mapstruct.DataDictItemMapping;
import in.hocg.eagle.mapstruct.qo.datadict.DataDictItemPostQo;
import in.hocg.eagle.mapstruct.qo.datadict.DataDictItemPutQo;
import in.hocg.eagle.mapstruct.qo.datadict.DataDictItemsPostQo;
import in.hocg.eagle.modules.base.entity.DataDictItem;
import in.hocg.eagle.modules.base.mapper.DataDictItemMapper;
import in.hocg.eagle.modules.base.service.DataDictItemService;
import in.hocg.eagle.utils.VerifyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
    public void insertOne(Long dictId, DataDictItemPostQo qo) {
        DataDictItem entity = mapping.asDataDictItem(qo);
        entity.setDictId(dictId);
        entity.setCreator(qo.getUserId());
        entity.setCreatedAt(qo.getCreatedAt());
        insert(entity);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletes(IdsQo qo) {
        for (Long id : qo.getId()) {
            removeById(id);
        }
    }
    
    
    @Override
    public void deleteByDictId(Long dictId) {
        baseMapper.deleteByDictId(dictId);
    }
    
    @Override
    public List<DataDictItem> selectListByDictId(Long dictId) {
        return baseMapper.selectList(dictId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertList(DataDictItemsPostQo qo) {
        for (DataDictItemPostQo item : qo.getItems()) {
            insertOne(qo.getDictId(), item);
        }
    }
    
    @Override
    public void updateOne(DataDictItemPutQo qo) {
        DataDictItem entity = mapping.asDataDictItem(qo);
        entity.setLastUpdater(qo.getUserId());
        entity.setLastUpdatedAt(qo.getCreatedAt());
        update(entity);
    }
    
    private void update(DataDictItem entity) {
        verifyEntity(entity);
        updateById(entity);
    }
    
    private void insert(DataDictItem entity) {
        verifyEntity(entity);
        baseMapper.insert(entity);
    }
    
    private boolean hasDictIdAndCodeIgnoreId(Long dictId, String code, Long ignoreId) {
        return baseMapper.countDictIdAndCodeIgnoreId(dictId, code, ignoreId) > 0;
    }
    
    /**
     * 入库时, 检查实体
     *
     * @param entity
     */
    @Override
    public void verifyEntity(DataDictItem entity) {
        final String code = entity.getCode();
        final Long dictId = entity.getDictId();
        final Long id = entity.getId();
        
        // 检查数据字典码
        if (Objects.nonNull(code)) {
            VerifyUtils.notNull(dictId);
            VerifyUtils.isFalse(hasDictIdAndCodeIgnoreId(dictId, code, id), "数据字典码已经存在");
        }
    }
}
