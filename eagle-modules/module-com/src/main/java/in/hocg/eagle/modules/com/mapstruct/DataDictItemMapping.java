package in.hocg.eagle.modules.com.mapstruct;

import in.hocg.eagle.modules.com.pojo.qo.datadict.item.DataDictItemInsertQo;
import in.hocg.eagle.modules.com.pojo.qo.datadict.item.DataDictItemUpdateQo;
import in.hocg.eagle.modules.com.pojo.vo.datadict.item.DataDictItemComplexVo;
import in.hocg.eagle.modules.com.pojo.vo.datadict.item.DataDictItemVo;
import in.hocg.eagle.modules.com.entity.DataDictItem;
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
public interface DataDictItemMapping {

    @Mapping(target = "enabledName", ignore = true)
    @Mapping(target = "lastUpdaterName", ignore = true)
    @Mapping(target = "creatorName", ignore = true)
    DataDictItemVo asDataDictItemVo(DataDictItem entity);

    List<DataDictItemVo> asDataDictItemVo(List<DataDictItem> entity);

    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dictId", ignore = true)
    @Mapping(target = "creator", ignore = true)
    DataDictItem asDataDictItem(DataDictItemInsertQo item);

    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "creator", ignore = true)
    DataDictItem asDataDictItem(DataDictItemUpdateQo qo);

    @Mapping(target = "lastUpdaterName", ignore = true)
    @Mapping(target = "enabledName", ignore = true)
    @Mapping(target = "creatorName", ignore = true)
    DataDictItemComplexVo asDataDictItemComplexVo(DataDictItem byId);
}
