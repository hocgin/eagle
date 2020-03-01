package in.hocg.eagle.modules.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.basic.pojo.KeyValue;
import in.hocg.eagle.mapstruct.qo.datadict.DataDictDeleteQo;
import in.hocg.eagle.mapstruct.qo.datadict.DataDictPostQo;
import in.hocg.eagle.mapstruct.qo.datadict.DataDictPutQo;
import in.hocg.eagle.mapstruct.qo.datadict.DataDictSearchQo;
import in.hocg.eagle.mapstruct.vo.DataDictComplexVo;
import in.hocg.eagle.mapstruct.vo.DataDictSearchVo;
import in.hocg.eagle.modules.base.entity.DataDict;
import in.hocg.eagle.modules.base.entity.DataDictItem;

import java.util.List;
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
    
    List<KeyValue> selectListDictItemByCode(String code);
    
    IPage<DataDictSearchVo> search(DataDictSearchQo qo);
    
    DataDictComplexVo selectOne(Long id);
    
    void insertOne(DataDictPostQo qo);
    
    void updateOne(DataDictPutQo qo);
    
    void deletes(DataDictDeleteQo qo);
}
