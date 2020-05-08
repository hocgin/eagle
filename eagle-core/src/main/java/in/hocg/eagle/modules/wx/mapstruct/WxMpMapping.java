package in.hocg.eagle.modules.wx.mapstruct;

import in.hocg.eagle.basic.constant.datadict.wx.WxMaterialType;
import in.hocg.eagle.modules.wx.entity.WxMpConfig;
import in.hocg.eagle.utils.string.JsonUtils;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.bean.menu.WxMenuRule;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/4/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface WxMpMapping {

    default WxMpConfigStorage asWxMpConfigStorage(WxMpConfig config) {
        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        configStorage.setAppId(config.getAppid());
        configStorage.setSecret(config.getAppSecret());
        configStorage.setToken(config.getToken());
        configStorage.setAesKey(config.getAesKey());
        return configStorage;
    }

    default WxMenu asWxMenu(in.hocg.eagle.modules.wx.entity.WxMenu entity) {
        final WxMenu result = new WxMenu();
        result.setButtons(JsonUtils.parseArray(entity.getButton(), WxMenuButton.class));
        result.setMatchRule(JsonUtils.parseObject(entity.getButton(), WxMenuRule.class));
        return result;
    }

    @Mapping(target = "url", ignore = true)
    WxMpMaterialNews.WxMpMaterialNewsArticle asWxMpMaterialNews0WxMpMaterialNewsArticle(WxMaterialType.News.NewsItem item);
}
