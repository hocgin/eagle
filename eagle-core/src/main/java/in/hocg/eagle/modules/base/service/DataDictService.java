package in.hocg.eagle.modules.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.basic.pojo.KeyValue;
import in.hocg.eagle.modules.base.pojo.qo.datadict.DataDictDeleteQo;
import in.hocg.eagle.modules.base.pojo.qo.datadict.DataDictInsertQo;
import in.hocg.eagle.modules.base.pojo.qo.datadict.DataDictUpdateQo;
import in.hocg.eagle.modules.base.pojo.qo.datadict.DataDictSearchQo;
import in.hocg.eagle.modules.base.pojo.vo.datadict.DataDictComplexVo;
import in.hocg.eagle.modules.base.pojo.vo.datadict.DataDictSearchVo;
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

    void insertOne(DataDictInsertQo qo);

    void updateOne(DataDictUpdateQo qo);

    void batchDelete(DataDictDeleteQo qo);

}
