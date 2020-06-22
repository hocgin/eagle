package in.hocg.eagle.modules.com.mapstruct;

import in.hocg.eagle.modules.com.entity.SystemSettings;
import in.hocg.eagle.modules.com.pojo.qo.systemsettings.SystemSettingsSaveQo;
import in.hocg.eagle.modules.com.pojo.vo.systemsettings.SystemSettingsComplexVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/2/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface SystemSettingsMapping {


    @Mapping(target = "lastUpdaterName", ignore = true)
    @Mapping(target = "creatorName", ignore = true)
    SystemSettingsComplexVo asSystemSettingsComplexVo(SystemSettings entity);

    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    SystemSettings asSystemSettings(SystemSettingsSaveQo qo);
}
