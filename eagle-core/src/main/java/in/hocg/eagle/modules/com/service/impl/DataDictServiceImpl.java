package in.hocg.eagle.modules.com.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.basic.pojo.KeyValue;
import in.hocg.eagle.mapstruct.DataDictItemMapping;
import in.hocg.eagle.mapstruct.DataDictMapping;
import in.hocg.eagle.modules.com.pojo.qo.datadict.*;
import in.hocg.eagle.modules.com.pojo.vo.datadict.DataDictComplexVo;
import in.hocg.eagle.modules.com.pojo.vo.datadict.DataDictSearchVo;
import in.hocg.eagle.modules.com.pojo.vo.datadict.item.DataDictItemVo;
import in.hocg.eagle.modules.com.entity.DataDict;
import in.hocg.eagle.modules.com.entity.DataDictItem;
import in.hocg.eagle.modules.com.mapper.DataDictMapper;
import in.hocg.eagle.modules.com.pojo.qo.datadict.item.DataDictItemInsertQo;
import in.hocg.eagle.modules.com.service.DataDictItemService;
import in.hocg.eagle.modules.com.service.DataDictService;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * [基础模块] 数据字典表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-14
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class DataDictServiceImpl extends AbstractServiceImpl<DataDictMapper, DataDict>
    implements DataDictService {
    private final DataDictMapping mapping;
    private final DataDictItemMapping dataDictItemMapping;
    private final DataDictItemService dataDictItemService;

    @Override
    public Optional<DataDictItem> selectOneByDictIdAndCode(String typeCode, String itemCode) {
        return baseMapper.selectOneByDictIdAndCode(typeCode, itemCode);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<DataDictItemVo> selectListDictItemByCodeAndEnabled(String typeCode, Integer enabled) {
        final List<DataDictItem> result = baseMapper.selectListDictItemByCodeAndEnabled(typeCode, enabled);
        return dataDictItemMapping.asDataDictItemVo(result);
    }

    @Override
    public List<KeyValue> selectListDictItemByCode(String typeCode) {
        return baseMapper.selectListDictItemByCodeAndEnabled(typeCode, Enabled.On.getCode())
            .stream()
            .map(item -> new KeyValue().setKey(item.getTitle()).setValue(item.getCode()))
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<DataDictSearchVo> search(DataDictSearchQo qo) {
        final IPage<DataDict> result = baseMapper.search(qo, qo.page());
        return result.convert(dataDict -> {
            final List<DataDictItemVo> items = selectListDictItemByCodeAndEnabled(dataDict.getCode(), null);
            return mapping.asDataDictSearchVo(dataDict, items);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataDictComplexVo selectOne(Long id) {
        final DataDict dataDict = baseMapper.selectById(id);
        if (Objects.isNull(dataDict)) {
            return null;
        }
        final String typeCode = dataDict.getCode();
        final List<DataDictItemVo> items = selectListDictItemByCodeAndEnabled(typeCode, Enabled.On.getCode());

        return mapping.asDataDictComplexVo(dataDict, items);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOne(DataDictInsertQo qo) {
        DataDict entity = mapping.asDataDict(qo);
        entity.setCreatedAt(qo.getCreatedAt());
        entity.setCreator(qo.getUserId());
        validInsert(entity);
        for (DataDictItemInsertQo item : qo.getItems()) {
            dataDictItemService.insertOne(entity.getId(), item);
        }
    }

    @Override
    public void updateOne(DataDictUpdateQo qo) {
        DataDict entity = mapping.asDataDict(qo);
        entity.setLastUpdatedAt(qo.getCreatedAt());
        entity.setLastUpdater(qo.getUserId());
        validUpdateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(DataDictDeleteQo qo) {
        for (Long id : qo.getId()) {
            deleteById(id, qo.getForce());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id, boolean force) {
        if (force) {
            deleteById(id);
        } else if (dataDictItemService.selectListByDictId(id).isEmpty()) {
            deleteById(id);
        } else {
            throw ServiceException.wrap("该字典存在字典项，无法进行删除");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        dataDictItemService.deleteByDictId(id);
        baseMapper.deleteById(id);
    }


    private boolean hasCode(String code, Long id) {
        return baseMapper.countByCodeIgnoreId(code, id) > 0;
    }

    @Override
    public void validEntity(DataDict entity) {
        final String code = entity.getCode();
        final Long id = entity.getId();

        // 检查数据字典码
        if (Objects.nonNull(code)) {
            ValidUtils.isFalse(hasCode(code, id), "数据字典码已经存在");
        }
    }
}