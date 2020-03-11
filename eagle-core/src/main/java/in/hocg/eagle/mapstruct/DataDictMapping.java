package in.hocg.eagle.mapstruct;

import in.hocg.eagle.modules.base.pojo.qo.datadict.DataDictInsertQo;
import in.hocg.eagle.modules.base.pojo.qo.datadict.DataDictUpdateQo;
import in.hocg.eagle.modules.base.pojo.vo.datadict.DataDictComplexVo;
import in.hocg.eagle.modules.base.pojo.vo.datadict.item.DataDictItemVo;
import in.hocg.eagle.modules.base.pojo.vo.datadict.DataDictSearchVo;
import in.hocg.eagle.modules.base.entity.DataDict;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Created by hocgin on 2020/3/1.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface DataDictMapping {
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "enabledName", ignore = true)
    DataDictSearchVo asDataDictSearchVo(DataDict entity);

    default DataDictSearchVo asDataDictSearchVo(DataDict entity, List<DataDictItemVo> items) {
        final DataDictSearchVo result = asDataDictSearchVo(entity);
        result.setItems(items);
        return result;
    }

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "lastUpdaterName", ignore = true)
    @Mapping(target = "enabledName", ignore = true)
    @Mapping(target = "creatorName", ignore = true)
    DataDictComplexVo asDataDictComplexVo(DataDict entity);

    default DataDictComplexVo asDataDictComplexVo(DataDict entity, List<DataDictItemVo> items) {
        final DataDictComplexVo result = asDataDictComplexVo(entity);
        result.setItems(items);
        return result;
    }

    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    DataDict asDataDict(DataDictInsertQo qo);

    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "creator", ignore = true)
    DataDict asDataDict(DataDictUpdateQo qo);
}
