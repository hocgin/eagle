package in.hocg.eagle.modules.wx.manager;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.constant.datadict.wx.WxMaterialType;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.modules.wx.entity.WxMenu;
import in.hocg.eagle.modules.wx.entity.WxMpConfig;
import in.hocg.eagle.modules.wx.entity.WxUser;
import in.hocg.eagle.modules.wx.mapstruct.WxMpMapping;
import in.hocg.eagle.utils.DateUtils;
import in.hocg.eagle.utils.ValidUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMaterialService;
import me.chanjar.weixin.mp.api.WxMpMenuService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.material.*;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by hocgin on 2020/4/25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class WxMpManager {
    private final WxMpService wxMpService;
    private final WxMpMapping mapping;

    /**
     * 获取图片或音频文件流
     *
     * @param appid
     * @param mediaId
     * @return
     */
    public InputStream getMaterialImageOrVoice(@NonNull String appid, String mediaId) {
        checkAndSwitchover(appid);
        final WxMpMaterialService materialService = wxMpService.getMaterialService();
        try {
            return materialService.materialImageOrVoiceDownload(mediaId);
        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        }
    }

    /**
     * 获取视频文件流
     *
     * @param appid
     * @param mediaId
     * @return
     */
    public InputStream getMaterialVideo(@NonNull String appid, String mediaId) {
        checkAndSwitchover(appid);
        final WxMpMaterialService materialService = wxMpService.getMaterialService();
        try {
            final WxMpMaterialVideoInfoResult result = materialService.materialVideoInfo(mediaId);
            return new URL(result.getDownUrl()).openStream();
        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        } catch (IOException e) {
            throw ServiceException.wrap(e.getMessage());
        }
    }

    /**
     * 上传图文素材
     *
     * @param appid
     * @param inArticles
     * @return
     */
    public WxMpMaterialUploadResult uploadMaterialNews(@NonNull String appid, List<WxMaterialType.News.NewsItem> inArticles) {
        checkAndSwitchover(appid);
        final WxMpMaterialService materialService = wxMpService.getMaterialService();
        final WxMpMaterialNews material = new WxMpMaterialNews();
        inArticles.parallelStream()
            .map(mapping::asWxMpMaterialNews0WxMpMaterialNewsArticle)
            .forEach(material::addArticle);
        try {
            return materialService.materialNewsUpload(material);
        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        }
    }

    public boolean updateMaterialNews(@NonNull String appid,
                                      @NonNull String mediaId,
                                      @NonNull Integer index,
                                      WxMaterialType.News.NewsItem item) {
        checkAndSwitchover(appid);
        final WxMpMaterialService materialService = wxMpService.getMaterialService();
        final WxMpMaterialNews.WxMpMaterialNewsArticle article = mapping.asWxMpMaterialNews0WxMpMaterialNewsArticle(item);
        final WxMpMaterialArticleUpdate update = new WxMpMaterialArticleUpdate();
        update.setMediaId(mediaId);
        update.setIndex(index);
        update.setArticles(article);
        try {
            return materialService.materialNewsUpdate(update);
        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        }
    }

    /**
     * 上传图文消息内的图片
     *
     * @param appid
     * @param file
     * @return
     */
    public String uploadMaterialImageWithNews(@NotNull String appid, @NotNull File file) {
        checkAndSwitchover(appid);
        final WxMpMaterialService materialService = wxMpService.getMaterialService();
        try {
            return materialService.mediaImgUpload(file).getUrl();
        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        }
    }

    /**
     * 上传永久素材(视频)
     *
     * @param appid
     * @param file
     * @param videoTitle
     * @param videoIntroduction
     * @return
     */
    public WxMpMaterialUploadResult uploadMaterialVideo(@NotNull String appid,
                                                        @NotNull File file,
                                                        String videoTitle,
                                                        String videoIntroduction) {
        checkAndSwitchover(appid);
        final WxMpMaterial material = new WxMpMaterial();
        material.setFile(file);
        material.setName(file.getName());
        material.setVideoTitle(videoTitle);
        material.setVideoIntroduction(videoIntroduction);
        final WxMpMaterialService materialService = wxMpService.getMaterialService();
        try {
            return materialService.materialFileUpload(WxConsts.MediaFileType.VIDEO, material);
        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        }
    }

    /**
     * 上传永久素材, 获取 media Id/url
     * 注: 非图文/非视频
     *
     * @param appid
     * @param mediaType
     * @param file
     * @return
     */
    public WxMpMaterialUploadResult uploadMaterialFile(@NotNull String appid,
                                                       @NotNull String mediaType,
                                                       @NotNull File file) {
        ValidUtils.isTrue(Lists.newArrayList(WxConsts.MediaFileType.THUMB,
            WxConsts.MediaFileType.VOICE,
            WxConsts.MediaFileType.IMAGE,
            WxConsts.MediaFileType.FILE).contains(mediaType), "文件类型不支持");

        checkAndSwitchover(appid);
        final WxMpMaterial material = new WxMpMaterial();
        material.setFile(file);
        material.setName(file.getName());
        final WxMpMaterialService materialService = wxMpService.getMaterialService();
        try {
            return materialService.materialFileUpload(mediaType, material);
        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        }
    }

    /**
     * 设置通用微信菜单
     *
     * @param entity
     */
    @SneakyThrows
    public void setWxGeneralMenu(WxMenu entity) {
        final String appid = entity.getAppid();
        checkAndSwitchover(appid);
        final WxMpMenuService menuService = wxMpService.getMenuService();
        menuService.menuCreate(mapping.asWxMenu(entity));
    }

    /**
     * 删除通用微信菜单
     *
     * @param entity
     */
    @SneakyThrows
    public void removeWxGeneralMenu(WxMenu entity) {
        final String appid = entity.getAppid();
        final WxMpMenuService menuService = wxMpService.getMenuService();
        checkAndSwitchover(appid);
        menuService.menuDelete();
    }

    /**
     * 设置个性化微信菜单
     *
     * @param entity
     */
    @SneakyThrows
    public void setWxIndividuationMenu(WxMenu entity) {
        final String appid = entity.getAppid();
        checkAndSwitchover(appid);
        final WxMpMenuService menuService = wxMpService.getMenuService();
        menuService.menuCreate(mapping.asWxMenu(entity));
    }

    /**
     * 删除个性化微信菜单
     *
     * @param entity
     */
    @SneakyThrows
    public void removeWxIndividuationMenu(WxMenu entity) {
        final String appid = entity.getAppid();
        final String menuId = entity.getMenuId();
        if (Objects.isNull(menuId)) {
            return;
        }
        final WxMpMenuService menuService = wxMpService.getMenuService();
        checkAndSwitchover(appid);
        menuService.menuDelete(menuId);
    }

    /**
     * 获取微信菜单
     *
     * @param appid
     * @return
     */
    public Optional<WxMpMenu> getWxMenus(@NotNull String appid) {
        checkAndSwitchover(appid);
        final WxMpMenuService menuService = wxMpService.getMenuService();
        try {
            return Optional.ofNullable(menuService.menuGet());
        } catch (WxErrorException e) {
            return Optional.empty();
        }
    }

    /**
     * 获取微信用户信息
     *
     * @param appid
     * @param openId
     * @return
     */
    public Optional<WxUser> getWxUser(@NotNull String appid, @NotNull String openId) {
        checkAndSwitchover(appid);
        final WxMpUserService userService = wxMpService.getUserService();
        try {
            final WxMpUser wxMpUser = userService.userInfo(openId);
            return Optional.of(new WxUser()
                .setAppid(appid)
                .setUnionid(wxMpUser.getUnionId())
                .setSubscribeScene(wxMpUser.getSubscribeScene())
                .setSubscribeTime(DateUtils.ofLong(wxMpUser.getSubscribeTime()))
                .setSex(wxMpUser.getSex())
                .setSubscribe(wxMpUser.getSubscribe())
                .setNickname(wxMpUser.getNickname())
                .setLanguage(wxMpUser.getLanguage())
                .setQrSceneStr(wxMpUser.getQrSceneStr())
                .setQrScene(wxMpUser.getQrScene())
                .setHeadimgurl(wxMpUser.getHeadImgUrl())
                .setCity(wxMpUser.getCity())
                .setCountry(wxMpUser.getCountry())
                .setProvince(wxMpUser.getProvince())
                .setOpenid(openId));
        } catch (WxErrorException e) {
            log.error("未找到微信用户信息", e);
        }
        return Optional.empty();
    }

    @Async
    public void insertOrUpdateWithMpConfig(@NonNull WxMpConfig config) {
        wxMpService.addConfigStorage(config.getAppid(), mapping.asWxMpConfigStorage(config));
    }

    @Async
    public void removeWithMpConfig(@NonNull String appid) {
        wxMpService.removeConfigStorage(appid);
    }

    public WxMpService getWxMpService() {
        return this.wxMpService;
    }

    /**
     * 切换公众号
     *
     * @param appid
     */
    private void checkAndSwitchover(String appid) {
        if (!wxMpService.switchover(appid)) {
            throw ServiceException.wrap("切换公众号配置失败[appid={}]", appid);
        }
    }
}
