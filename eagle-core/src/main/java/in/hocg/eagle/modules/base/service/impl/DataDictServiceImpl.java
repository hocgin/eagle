package in.hocg.eagle.modules.base.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.modules.base.entity.DataDict;
import in.hocg.eagle.modules.base.entity.DataDictItem;
import in.hocg.eagle.modules.base.mapper.DataDictMapper;
import in.hocg.eagle.modules.base.service.DataDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    
    @Override
    public Optional<DataDictItem> selectOneByDictIdAndCode(String typeCode, String itemCode) {
        return baseMapper.selectOneByDictIdAndCode(typeCode, itemCode);
    }
}
