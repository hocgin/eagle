package in.hocg.eagle.modules.wx.mapstruct;

import in.hocg.eagle.modules.wx.entity.WxMenu;
import in.hocg.eagle.modules.wx.pojo.qo.menu.WxMenuInsertQo;
import in.hocg.eagle.modules.wx.pojo.qo.menu.WxMenuUpdateQo;
import in.hocg.eagle.modules.wx.pojo.vo.menu.WxMenuComplexVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/4/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface WxMenuMapping {
    @Mapping(target = "appid", ignore = true)
    @Mapping(target = "menuType", ignore = true)
    @Mapping(target = "matchRule", ignore = true)
    @Mapping(target = "menuId", ignore = true)
    @Mapping(target = "button", ignore = true)
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "creator", ignore = true)
    WxMenu asWxMenu(WxMenuUpdateQo qo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "menuType", ignore = true)
    @Mapping(target = "menuId", ignore = true)
    @Mapping(target = "button", ignore = true)
    @Mapping(target = "matchRule", ignore = true)
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "creator", ignore = true)
    WxMenu asWxMenu(WxMenuInsertQo qo);

    @Mapping(target = "button", ignore = true)
    @Mapping(target = "matchRule", ignore = true)
    WxMenuComplexVo asWxMenuComplexVo(WxMenu entity);
}
