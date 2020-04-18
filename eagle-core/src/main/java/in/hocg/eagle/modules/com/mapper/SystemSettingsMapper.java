package in.hocg.eagle.modules.com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.com.entity.SystemSettings;
import in.hocg.eagle.modules.com.pojo.qo.systemsettings.SystemSettingsPagingQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [基础模块] 系统配置表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
@Mapper
public interface SystemSettingsMapper extends BaseMapper<SystemSettings> {

    IPage<SystemSettings> pagingWithComplex(@Param("qo") SystemSettingsPagingQo qo, @Param("page") Page page);

    Integer countAndConfigCodeIgnoreId(@Param("code") String code, @Param("id") Long id);
}
