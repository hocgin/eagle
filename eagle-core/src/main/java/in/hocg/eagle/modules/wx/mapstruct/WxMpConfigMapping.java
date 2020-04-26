package in.hocg.eagle.modules.wx.mapstruct;

import in.hocg.eagle.modules.wx.entity.WxMpConfig;
import in.hocg.eagle.modules.wx.pojo.qo.WxMpConfigSaveQo;
import in.hocg.eagle.modules.wx.pojo.vo.WxMpConfigComplexVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/4/26.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface WxMpConfigMapping {
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    WxMpConfig asWxMpConfig(WxMpConfigSaveQo qo);

    WxMpConfigComplexVo asWxMpConfigComplex(WxMpConfig entity);
}
