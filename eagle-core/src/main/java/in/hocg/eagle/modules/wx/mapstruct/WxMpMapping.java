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
import org.apache.logging.log4j.util.Strings;
import org.mapstruct.Mapper;

import java.util.Objects;

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

    default WxMpMaterialNews.WxMpMaterialNewsArticle asWxMpMaterialNews0WxMpMaterialNewsArticle(WxMaterialType.News.NewsItem item) {
        final WxMpMaterialNews.WxMpMaterialNewsArticle result = new WxMpMaterialNews.WxMpMaterialNewsArticle();
        final String thumbUrl = item.getThumbUrl();
        final String thumbMediaId = item.getThumbMediaId();
        if (Strings.isNotBlank(thumbUrl)) {
            result.setThumbUrl(thumbUrl);
        } else if (Objects.nonNull(thumbMediaId)){
            result.setThumbMediaId(thumbMediaId);
        }
        result.setContent(item.getContent());
        result.setTitle(item.getTitle());
        result.setAuthor(item.getAuthor());
        result.setContentSourceUrl(item.getContentSourceUrl());
        result.setNeedOpenComment(item.getNeedOpenComment());
        result.setDigest(item.getDigest());
        result.setOnlyFansCanComment(item.getOnlyFansCanComment());
        result.setShowCoverPic(item.getShowCoverPic());
        return result;
    }

}
