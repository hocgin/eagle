package in.hocg.eagle.modules.base.service;

import in.hocg.eagle.modules.base.entity.DataDict;
import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.modules.base.entity.DataDictItem;

import java.util.Optional;

/**
 * <p>
 * [基础模块] 数据字典表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-14
 */
public interface DataDictService extends AbstractService<DataDict> {
    
    Optional<DataDictItem> selectOneByDictIdAndCode(String typeCode, String itemCode);
}
