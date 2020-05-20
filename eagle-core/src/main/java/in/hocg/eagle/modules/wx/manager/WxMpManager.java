package in.hocg.eagle.modules.wx.manager;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.constant.datadict.wx.WxMaterialType;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.modules.wx.entity.*;
import in.hocg.eagle.modules.wx.mapstruct.WxMpMapping;
import in.hocg.eagle.modules.wx.pojo.qo.message.SendTemplateMessageToUserQo;
import in.hocg.eagle.utils.DateUtils;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.*;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.WxMpMassPreviewMessage;
import me.chanjar.weixin.mp.bean.WxMpMassTagMessage;
import me.chanjar.weixin.mp.bean.material.*;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import me.chanjar.weixin.mp.bean.tag.WxTagListUser;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;
import me.chanjar.weixin.mp.bean.template.WxMpTemplate;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
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
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
     * [标签] 删除标签
     *
     * @param appid
     * @param tagId
     */
    public void deleteTag(@NonNull String appid, @NonNull Long tagId) {
        final WxMpUserTagService service = this.getWxMpService(appid).getUserTagService();
        try {
            service.tagDelete(tagId);
        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        }

    }

    /**
     * [标签] 取消标签
     *
     * @param appid
     * @param tagId
     * @param openId
     * @return
     */
    public boolean unsetUserTag(@NonNull String appid, @NonNull Long tagId, @NonNull List<String> openId) {
        final WxMpUserTagService service = this.getWxMpService(appid).getUserTagService();
        try {
            return service.batchUntagging(tagId, openId.toArray(new String[]{}));
        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        }
    }

    /**
     * [标签] 设置标签
     *
     * @param appid
     * @param tagId
     * @param openId
     * @return
     */
    public boolean setUserTag(@NonNull String appid, @NonNull Long tagId, @NonNull List<String> openId) {
        final WxMpUserTagService service = this.getWxMpService(appid).getUserTagService();
        try {
            return service.batchTagging(tagId, openId.toArray(new String[]{}));
        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        }
    }

    /**
     * 获取标签列表
     *
     * @param appid
     * @param consumer
     */
    public void getTags(@NonNull String appid, Consumer<WxMpUserTags> consumer) {
        final WxMpUserTagService service = this.getWxMpService(appid).getUserTagService();
        try {
            final List<WxUserTag> tags = service.tagGet();
            tags.stream().map(wxUserTag -> mapping.asWxMpUserTags(appid, wxUserTag)).forEach(consumer);
        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        }

    }

    /**
     * 拉取指定标签下的所有用户列表
     *
     * @param appid
     * @param tagId
     * @param nextOpenId
     * @param consumer
     */
    public void getTagUsers(@NonNull String appid, @NonNull Long tagId, String nextOpenId,
                            Consumer<String> consumer) {
        final WxMpUserTagService service = this.getWxMpService(appid).getUserTagService();
        String tmpNextOpenId = nextOpenId;
        try {
            do {
                final WxTagListUser result = service.tagListUser(tagId, tmpNextOpenId);
                result.getData().getOpenidList().forEach(consumer);
                tmpNextOpenId = result.getNextOpenid();
            } while (Strings.isNotBlank(tmpNextOpenId));
        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        }
    }

    /**
     * 获取微信用户列表
     *
     * @param appid
     * @param nextOpenId
     * @param consumer
     */
    public void getWxUserList(@NonNull String appid, String nextOpenId,
                              Consumer<String> consumer) {
        try {
            final WxMpUserService service = getWxMpService(appid).getUserService();
            String tmpNextOpenId = nextOpenId;
            do {
                WxMpUserList result = service.userList(tmpNextOpenId);
                result.getOpenids().forEach(consumer);
                tmpNextOpenId = result.getNextOpenid();
            } while (Strings.isNotBlank(tmpNextOpenId));

        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        }
    }

    /**
     * [消息/用户] 发送普通消息
     *
     * @param appid
     * @param openId
     * @param msgType
     * @param mediaId
     * @param content
     * @return
     */
    public WxMpMassSendResult massOpenIdsMessageSend(@NonNull String appid, @NonNull List<String> openId,
                                                     @NonNull String msgType, String mediaId, String content) {
        ValidUtils.isFalse(StringUtils.isAllBlank(mediaId, content), "发送内容不能为空");
        final WxMpMassMessageService service = getWxMpService(appid).getMassMessageService();
        final WxMpMassOpenIdsMessage message = new WxMpMassOpenIdsMessage();
        message.setToUsers(openId);
        message.setMsgType(msgType);
        if (Strings.isNotBlank(content)) {
            message.setContent(content);
        } else if (Strings.isNotBlank(mediaId)) {
            message.setMediaId(mediaId);
        } else {
            ValidUtils.fail("发送内容错误");
        }
        try {
            return service.massOpenIdsMessageSend(message);
        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        }
    }

    /**
     * [消息/群组] 发送普通消息
     *
     * @param appid
     * @param tagId
     * @param msgType
     * @param mediaId
     * @param content
     * @return
     */
    public WxMpMassSendResult massGroupMessageSend(@NonNull String appid, Long tagId,
                                                   @NonNull String msgType, String mediaId, String content) {
        ValidUtils.isFalse(StringUtils.isAllBlank(mediaId, content), "发送内容不能为空");
        getWxMpService(appid);
        final WxMpMassMessageService service = wxMpService.getMassMessageService();
        final WxMpMassTagMessage message = new WxMpMassTagMessage();
        message.setSendAll(Objects.isNull(tagId));
        message.setTagId(tagId);
        message.setMsgType(msgType);
        if (Strings.isNotBlank(content)) {
            message.setContent(content);
        } else if (Strings.isNotBlank(mediaId)) {
            message.setMediaId(mediaId);
        } else {
            ValidUtils.fail("发送内容错误");
        }
        try {
            return service.massGroupMessageSend(message);
        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        }
    }

    /**
     * [消息/用户] 发送预览消息
     *
     * @param appid
     * @param openId
     * @param msgType
     * @param mediaId
     * @param content
     * @return
     */
    public WxMpMassSendResult massMessagePreview(@NonNull String appid, @NonNull String openId,
                                                 @NonNull String msgType, String mediaId, String content) {
        ValidUtils.isFalse(StringUtils.isAllBlank(mediaId, content), "发送内容不能为空");
        final WxMpMassMessageService service = getWxMpService(appid).getMassMessageService();
        try {
            final WxMpMassPreviewMessage message = new WxMpMassPreviewMessage();
            message.setToWxUserOpenid(openId);
            message.setMsgType(msgType);
            if (Strings.isNotBlank(content)) {
                message.setContent(content);
            } else if (Strings.isNotBlank(mediaId)) {
                message.setMediaId(mediaId);
            } else {
                ValidUtils.fail("发送内容错误");
            }
            return service.massMessagePreview(message);
        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        }
    }

    /**
     * [消息/用户] 发送模版消息
     *
     * @param appid
     * @param openId
     * @param templateId
     * @param templateData
     * @param url
     * @param miniProgram
     * @return 消息Id
     */
    public String sendTemplateMsg(@NonNull String appid, @NonNull String openId, @NonNull String templateId,
                                  List<SendTemplateMessageToUserQo.TemplateData> templateData, String url, SendTemplateMessageToUserQo.MiniProgram miniProgram) {
        final WxMpTemplateMsgService service = getWxMpService(appid).getTemplateMsgService();
        final WxMpTemplateMessage message = new WxMpTemplateMessage();
        message.setTemplateId(templateId);
        message.setToUser(openId);
        message.setData(templateData.parallelStream().map(SendTemplateMessageToUserQo.TemplateData::asWxMpTemplateData).collect(Collectors.toList()));
        LangUtils.setIfNotNull(LangUtils.callIfNotNull(miniProgram, SendTemplateMessageToUserQo.MiniProgram::asMiniProgram).orElse(null), message::setMiniProgram);
        LangUtils.setIfNotNull(url, message::setUrl);

        try {
            return service.sendTemplateMsg(message);
        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        }
    }

    /**
     * [消息模版] 查询所有
     *
     * @param appid
     * @return
     */
    public List<WxMpMessageTemplate> getAllPrivateTemplate(@NonNull String appid) {
        final WxMpTemplateMsgService service = getWxMpService(appid).getTemplateMsgService();
        try {
            final List<WxMpTemplate> allPrivateTemplate = service.getAllPrivateTemplate();
            return allPrivateTemplate.parallelStream()
                .map(template -> mapping.asWxMpMessageTemplate(template).setAppid(appid))
                .collect(Collectors.toList());
        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        }
    }

    /**
     * [素材] 获取图片或音频文件流
     *
     * @param appid
     * @param mediaId
     * @return
     */
    public InputStream getMaterialImageOrVoice(@NonNull String appid, String mediaId) {
        final WxMpMaterialService service = getWxMpService(appid).getMaterialService();
        try {
            return service.materialImageOrVoiceDownload(mediaId);
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
        final WxMpMaterialService materialService = getWxMpService(appid).getMaterialService();
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
        final WxMpMaterialService materialService = getWxMpService(appid).getMaterialService();
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
        final WxMpMaterialService materialService = getWxMpService(appid).getMaterialService();
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
        final WxMpMaterialService materialService = getWxMpService(appid).getMaterialService();
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
        final WxMpMaterialService materialService = getWxMpService(appid).getMaterialService();
        final WxMpMaterial material = new WxMpMaterial();
        material.setFile(file);
        material.setName(file.getName());
        material.setVideoTitle(videoTitle);
        material.setVideoIntroduction(videoIntroduction);
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

        final WxMpMaterialService materialService = getWxMpService(appid).getMaterialService();
        final WxMpMaterial material = new WxMpMaterial();
        material.setFile(file);
        material.setName(file.getName());
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
    public void setWxGeneralMenu(WxMenu entity) {
        final String appid = entity.getAppid();
        final WxMpMenuService menuService = getWxMpService(appid).getMenuService();
        try {
            final me.chanjar.weixin.common.bean.menu.WxMenu wxMenu = mapping.asWxMenu(entity);
            wxMenu.setMatchRule(null);
            menuService.menuCreate(wxMenu);
        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        }
    }

    /**
     * 删除通用微信菜单
     *
     * @param entity
     */
    public void removeWxGeneralMenu(WxMenu entity) {
        final String appid = entity.getAppid();
        final WxMpMenuService menuService = getWxMpService(appid).getMenuService();
        try {
            menuService.menuDelete();
        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        }
    }

    /**
     * 设置个性化微信菜单
     *
     * @param entity
     */
    public String setWxIndividuationMenu(WxMenu entity) {
        final String appid = entity.getAppid();
        final WxMpMenuService menuService = getWxMpService(appid).getMenuService();
        try {
            return menuService.menuCreate(mapping.asWxMenu(entity));
        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        }
    }

    /**
     * 删除个性化微信菜单
     *
     * @param entity
     */
    public void removeWxIndividuationMenu(WxMenu entity) {
        final String appid = entity.getAppid();
        final String menuId = entity.getMenuId();
        if (Objects.isNull(menuId)) {
            return;
        }
        final WxMpMenuService menuService = getWxMpService(appid).getMenuService();
        try {
            menuService.menuDelete(menuId);
        } catch (WxErrorException e) {
            throw ServiceException.wrap(e.getError().getErrorMsg());
        }
    }

    /**
     * 获取微信菜单
     *
     * @param appid
     * @return
     */
    public Optional<WxMpMenu> getWxMenus(@NotNull String appid) {
        final WxMpMenuService menuService = getWxMpService(appid).getMenuService();
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
        final WxMpUserService userService = getWxMpService(appid).getUserService();
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
    private WxMpService getWxMpService(String appid) {
        try {
            return this.wxMpService.switchoverTo(appid);
        } catch (Exception e) {
            throw ServiceException.wrap(e);
        }
    }
}
