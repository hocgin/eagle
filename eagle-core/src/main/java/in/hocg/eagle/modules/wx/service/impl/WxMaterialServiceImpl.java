package in.hocg.eagle.modules.wx.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.IntEnum;
import in.hocg.eagle.basic.constant.datadict.wx.WxMaterialType;
import in.hocg.eagle.modules.wx.entity.WxMaterial;
import in.hocg.eagle.modules.wx.manager.WxMpManager;
import in.hocg.eagle.modules.wx.mapper.WxMaterialMapper;
import in.hocg.eagle.modules.wx.mapstruct.WxMaterialMapping;
import in.hocg.eagle.modules.wx.pojo.qo.material.WxMaterialUploadFileQo;
import in.hocg.eagle.modules.wx.pojo.qo.material.WxMaterialUploadNewsQo;
import in.hocg.eagle.modules.wx.pojo.qo.material.WxMaterialUploadVideoQo;
import in.hocg.eagle.modules.wx.service.WxMaterialService;
import in.hocg.eagle.utils.ValidUtils;
import in.hocg.eagle.utils.file.FileUtils;
import in.hocg.eagle.utils.string.JsonUtils;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * [微信模块] 微信素材库 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-05-05
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class WxMaterialServiceImpl extends AbstractServiceImpl<WxMaterialMapper, WxMaterial> implements WxMaterialService {
    private final WxMpManager wxMpManager;
    private final WxMaterialMapping mapping;


    @Override
    public void uploadVoice(WxMaterialUploadFileQo qo) {
        this.uploadFile(qo);
    }

    @Override
    public void uploadImage(WxMaterialUploadFileQo qo) {
        this.uploadFile(qo);
    }

    @Override
    public void uploadVideo(WxMaterialUploadVideoQo qo) {
        final String url = qo.getUrl();
        final String appid = qo.getAppid();
        final LocalDateTime createdAt = qo.getCreatedAt();
        final Long userId = qo.getUserId();
        final String videoTitle = qo.getVideoTitle();
        final String videoIntroduction = qo.getVideoIntroduction();

        File file = null;
        WxMpMaterialUploadResult result = null;
        try {
            file = FileUtils.toFile(new URL(url));
            result = wxMpManager.uploadMaterialVideo(appid, file, videoTitle, videoIntroduction);
        } catch (IOException e) {
            ValidUtils.fail(e);
        }

        final WxMaterial entity = new WxMaterial();
        entity.setAppid(appid);
        entity.setMaterialType(WxMaterialType.Video.getCode());
        entity.setCreatedAt(createdAt);
        entity.setCreator(userId);
        entity.setMaterialContent(JsonUtils.toJSONString(
            new WxMaterialType.Video()
                .setTitle(videoTitle)
                .setIntroduction(videoIntroduction)
                .setFilename(file.getName())
                .setUrl(url)
        ));
        entity.setMaterialResult(JsonUtils.toJSONString(
            new WxMaterialType.Result()
                .setMediaId(result.getMediaId())
                .setUrl(result.getUrl())
        ));
        validInsert(entity);
    }

    @Override
    public void uploadNews(WxMaterialUploadNewsQo qo) {
        final String appid = qo.getAppid();
        final LocalDateTime createdAt = qo.getCreatedAt();
        final Long userId = qo.getUserId();

        final List<WxMaterialType.News.NewsItem> items = qo.getNewsItems().stream()
            .map(newsItem -> {
                final WxMaterialType.News.NewsItem result = mapping.asWxMaterialType0News0NewsItem(newsItem);
                final String thumbMediaId = newsItem.getThumbMediaId();
                if (Strings.isNotBlank(thumbMediaId)) {
                    result.setThumbMediaId(thumbMediaId);
                } else {
                    final File file;
                    try {
                        file = FileUtils.toFile(new URL(newsItem.getOriginalUrl()));
                        final String url = wxMpManager.uploadMaterialImageWithNews(appid, file);
                        result.setThumbUrl(url);
                    } catch (IOException e) {
                        ValidUtils.fail(e.getMessage());
                    }
                }
                return result;
            }).collect(Collectors.toList());

        final WxMpMaterialUploadResult result = wxMpManager.uploadMaterialNews(appid, items);

        final WxMaterial entity = new WxMaterial();
        entity.setAppid(appid);
        entity.setMaterialType(WxMaterialType.News.getCode());
        entity.setCreatedAt(createdAt);
        entity.setCreator(userId);
        entity.setMaterialContent(JsonUtils.toJSONString(
            new WxMaterialType.News().addAll(items)
        ));
        entity.setMaterialResult(JsonUtils.toJSONString(
            new WxMaterialType.Result()
                .setMediaId(result.getMediaId())
                .setUrl(result.getUrl())
        ));
        validInsert(entity);
    }

    @Override
    public InputStream getVideoStream(String appid, String mediaId) {
        return wxMpManager.getMaterialVideo(appid, mediaId);
    }

    @Override
    public InputStream getVoiceStream(String appid, String mediaId) {
        return wxMpManager.getMaterialImageOrVoice(appid, mediaId);
    }

    @Override
    public InputStream getImageStream(String appid, String mediaId) {
        return wxMpManager.getMaterialImageOrVoice(appid, mediaId);
    }

    /**
     * 非图文/非视频
     *
     * @param qo
     */
    private void uploadFile(WxMaterialUploadFileQo qo) {
        final String url = qo.getUrl();
        final String appid = qo.getAppid();
        final LocalDateTime createdAt = qo.getCreatedAt();
        final Long userId = qo.getUserId();
        final WxMaterialType wxMaterialType = IntEnum.ofThrow(qo.getMaterialType(), WxMaterialType.class);
        final String mediaFileType = wxMaterialType.asMediaFileType();

        File file = null;
        WxMpMaterialUploadResult result = null;
        try {
            file = FileUtils.toFile(new URL(url));
            result = wxMpManager.uploadMaterialFile(appid, mediaFileType, file);
        } catch (IOException e) {
            ValidUtils.fail(e);
        }

        final WxMaterial entity = new WxMaterial();
        entity.setAppid(appid);
        entity.setMaterialType(wxMaterialType.getCode());
        entity.setCreatedAt(createdAt);
        entity.setCreator(userId);
        entity.setMaterialContent(JsonUtils.toJSONString(
            new WxMaterialType.File()
                .setFilename(file.getName())
                .setUrl(url)
        ));
        entity.setMaterialResult(JsonUtils.toJSONString(
            new WxMaterialType.Result()
                .setMediaId(result.getMediaId())
                .setUrl(result.getUrl())
        ));
        validInsert(entity);
    }
}
