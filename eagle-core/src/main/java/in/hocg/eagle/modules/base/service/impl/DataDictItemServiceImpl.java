package in.hocg.eagle.modules.base.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.pojo.qo.IdsQo;
import in.hocg.eagle.mapstruct.DataDictItemMapping;
import in.hocg.eagle.modules.base.pojo.qo.datadict.item.DataDictItemInsertQo;
import in.hocg.eagle.modules.base.pojo.qo.datadict.item.DataDictItemUpdateQo;
import in.hocg.eagle.modules.base.pojo.qo.datadict.item.DataDictItemsBatchInsertQo;
import in.hocg.eagle.modules.base.pojo.vo.datadict.item.DataDictItemComplexVo;
import in.hocg.eagle.modules.base.entity.DataDictItem;
import in.hocg.eagle.modules.base.mapper.DataDictItemMapper;
import in.hocg.eagle.modules.base.service.DataDictItemService;
import in.hocg.eagle.utils.ValidUtils;
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
    public void insertOne(Long dictId, DataDictItemInsertQo qo) {
        DataDictItem entity = mapping.asDataDictItem(qo);
        entity.setDictId(dictId);
        entity.setCreator(qo.getUserId());
        entity.setCreatedAt(qo.getCreatedAt());
        validEntity(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(IdsQo qo) {
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
        return baseMapper.selectListByDictId(dictId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchInsert(DataDictItemsBatchInsertQo qo) {
        for (DataDictItemInsertQo item : qo.getItems()) {
            insertOne(qo.getDictId(), item);
        }
    }

    @Override
    public void updateOne(DataDictItemUpdateQo qo) {
        DataDictItem entity = mapping.asDataDictItem(qo);
        entity.setLastUpdater(qo.getUserId());
        entity.setLastUpdatedAt(qo.getCreatedAt());
        validUpdateById(entity);
    }

    @Override
    public DataDictItemComplexVo selectOne(Long id) {
        return mapping.asDataDictItemComplexVo(getById(id));
    }

    private boolean hasDictIdAndCodeIgnoreId(Long dictId, String code, Long ignoreId) {
        return baseMapper.countDictIdAndCodeIgnoreId(dictId, code, ignoreId) > 0;
    }

    @Override
    public void validEntity(DataDictItem entity) {
        final String code = entity.getCode();
        final Long dictId = entity.getDictId();
        final Long id = entity.getId();

        // 检查数据字典码
        if (Objects.nonNull(code)) {
            ValidUtils.notNull(dictId);
            ValidUtils.isFalse(hasDictIdAndCodeIgnoreId(dictId, code, id), "数据字典码已经存在");
        }
    }
}
