package in.hocg.eagle.modules.base.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.mapstruct.qo.datadict.DataDictPostQo;
import in.hocg.eagle.modules.base.entity.DataDictItem;

import java.util.List;

/**
 * <p>
 * [基础模块] 数据字典项表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-14
 */
public interface DataDictItemService extends AbstractService<DataDictItem> {
    
    void insertOne(Long id, DataDictPostQo.DataDictItemPostQo item);
    
    void deleteByDictId(Long id);
    
    List<DataDictItem> selectListByDictId(Long dictId);
}
