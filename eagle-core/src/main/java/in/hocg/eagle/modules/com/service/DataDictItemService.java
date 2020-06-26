package in.hocg.eagle.modules.com.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.basic.pojo.ro.IdsRo;
import in.hocg.eagle.modules.com.pojo.qo.datadict.item.DataDictItemInsertQo;
import in.hocg.eagle.modules.com.pojo.qo.datadict.item.DataDictItemUpdateQo;
import in.hocg.eagle.modules.com.pojo.qo.datadict.item.DataDictItemsBatchInsertQo;
import in.hocg.eagle.modules.com.pojo.vo.datadict.item.DataDictItemComplexVo;
import in.hocg.eagle.modules.com.entity.DataDictItem;
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

    void insertOne(Long id, DataDictItemInsertQo item);

    @Transactional(rollbackFor = Exception.class)
    void batchDelete(IdsRo qo);

    void deleteByDictId(Long id);

    List<DataDictItem> selectListByDictId(Long dictId);

    void batchInsert(DataDictItemsBatchInsertQo qo);

    void updateOne(DataDictItemUpdateQo qo);

    DataDictItemComplexVo selectOne(Long id);
}
