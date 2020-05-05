package in.hocg.eagle.modules.wx.manager;

import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.modules.wx.entity.WxMenu;
import in.hocg.eagle.modules.wx.entity.WxMpConfig;
import in.hocg.eagle.modules.wx.entity.WxUser;
import in.hocg.eagle.modules.wx.mapstruct.WxMpMapping;
import in.hocg.eagle.utils.DateUtils;
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
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.File;
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
    private final WxMpMapping wxMpMapping;

    @SneakyThrows
    public WxMpMaterialUploadResult materialImageUpload(@NotNull String appid,
                                                        @NotNull File file) {
        checkAndSwitchover(appid);
        final WxMpMaterial material = new WxMpMaterial();
        material.setFile(file);
        material.setName(file.getName());
        final WxMpMaterialService materialService = wxMpService.getMaterialService();
        return materialService.materialFileUpload(WxConsts.MaterialType.IMAGE, material);
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
        menuService.menuCreate(wxMpMapping.asWxMenu(entity));
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
        menuService.menuCreate(wxMpMapping.asWxMenu(entity));
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
        wxMpService.addConfigStorage(config.getAppid(), wxMpMapping.asWxMpConfigStorage(config));
    }

    @Async
    public void removeWithMpConfig(@NonNull String appid) {
        wxMpService.removeConfigStorage(appid);
    }

    public WxMpService getWxMpService() {
        return this.wxMpService;
    }


    private void checkAndSwitchover(String appid) {
        if (!wxMpService.switchover(appid)) {
            throw ServiceException.wrap("切换公众号配置失败[appid={}]", appid);
        }
    }
}
