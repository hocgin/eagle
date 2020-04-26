package in.hocg.eagle.modules.wx.manager;

import in.hocg.eagle.modules.wx.entity.WxMpConfig;
import in.hocg.eagle.modules.wx.entity.WxUser;
import in.hocg.eagle.utils.DateUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMaterialService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
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

    @SneakyThrows
    public void up() {
        final WxMpMaterialService materialService = wxMpService.getMaterialService();
        materialService.materialNewsBatchGet(0, 20);
    }

    public Optional<WxUser> getWxUser(@NotNull String appId, @NotNull String openId) {
        final WxMpUserService userService = wxMpService.getUserService();
        try {
            final WxMpUser wxMpUser = userService.userInfo(openId);
            return Optional.of(new WxUser()
                .setAppid(appId)
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
        wxMpService.addConfigStorage(config.getAppid(), this.asWxMpConfigStorage(config));
    }

    @Async
    public void removeWithMpConfig(@NonNull String appid) {
        wxMpService.removeConfigStorage(appid);
    }

    public WxMpService getWxMpService() {
        return this.wxMpService;
    }

    public WxMpConfigStorage asWxMpConfigStorage(WxMpConfig config) {
        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        configStorage.setAppId(config.getAppid());
        configStorage.setSecret(config.getAppSecret());
        configStorage.setToken(config.getToken());
        configStorage.setAesKey(config.getAesKey());
        return configStorage;
    }

}
