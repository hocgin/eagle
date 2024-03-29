package in.hocg.eagle.modules.com.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractService;
import in.hocg.eagle.modules.com.entity.SystemSettings;
import in.hocg.eagle.modules.com.pojo.dto.SystemSettingInitDto;
import in.hocg.eagle.modules.com.pojo.qo.systemsettings.SystemSettingsPagingQo;
import in.hocg.eagle.modules.com.pojo.qo.systemsettings.SystemSettingsSaveQo;
import in.hocg.eagle.modules.com.pojo.vo.systemsettings.SystemSettingsComplexVo;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * [基础模块] 系统配置表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
public interface SystemSettingsService extends AbstractService<SystemSettings> {

    Optional<String> selectOneWithString(String configCode);

    IPage<SystemSettingsComplexVo> paging(SystemSettingsPagingQo qo);

    void saveOne(SystemSettingsSaveQo qo);

    SystemSettingsComplexVo selectOne(Long id);

    @Async
    void init(List<SystemSettingInitDto> items);
}
