package in.hocg.eagle.mapstruct;

import in.hocg.eagle.mapstruct.qo.datadict.DataDictItemPostQo;
import in.hocg.eagle.mapstruct.qo.datadict.DataDictItemPutQo;
import in.hocg.eagle.mapstruct.vo.datadict.item.DataDictItemVo;
import in.hocg.eagle.mapstruct.vo.datadict.item.DataDictItemComplexVo;
import in.hocg.eagle.modules.base.entity.DataDictItem;
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
    DataDictItem asDataDictItem(DataDictItemPostQo item);
    
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "dictId", ignore = true)
    @Mapping(target = "creator", ignore = true)
    DataDictItem asDataDictItem(DataDictItemPutQo qo);
    
    @Mapping(target = "lastUpdaterName", ignore = true)
    @Mapping(target = "enabledName", ignore = true)
    @Mapping(target = "creatorName", ignore = true)
    DataDictItemComplexVo asDataDictItemComplexVo(DataDictItem byId);
}
