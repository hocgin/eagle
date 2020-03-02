package in.hocg.eagle.modules.base.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.basic.pojo.qo.IdsQo;
import in.hocg.eagle.mapstruct.qo.datadict.DataDictItemPostQo;
import in.hocg.eagle.mapstruct.qo.datadict.DataDictItemPutQo;
import in.hocg.eagle.mapstruct.qo.datadict.DataDictItemsPostQo;
import in.hocg.eagle.mapstruct.vo.datadict.item.DataDictItemComplexVo;
import in.hocg.eagle.modules.base.entity.DataDictItem;
import org.springframework.transaction.annotation.Transactional;

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
    
    void insertOne(Long id, DataDictItemPostQo item);
    
    @Transactional(rollbackFor = Exception.class)
    void deletes(IdsQo qo);
    
    void deleteByDictId(Long id);
    
    List<DataDictItem> selectListByDictId(Long dictId);
    
    void insertList(DataDictItemsPostQo qo);
    
    void updateOne(DataDictItemPutQo qo);
    
    DataDictItemComplexVo selectOne(Long id);
}
