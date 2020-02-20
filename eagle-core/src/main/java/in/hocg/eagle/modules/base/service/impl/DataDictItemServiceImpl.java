package in.hocg.eagle.modules.base.service.impl;

import in.hocg.eagle.modules.base.entity.DataDictItem;
import in.hocg.eagle.modules.base.mapper.DataDictItemMapper;
import in.hocg.eagle.modules.base.service.DataDictItemService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

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

}
