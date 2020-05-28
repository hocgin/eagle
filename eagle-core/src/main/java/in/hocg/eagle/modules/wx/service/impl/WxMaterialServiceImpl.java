package in.hocg.eagle.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.IntEnum;
import in.hocg.eagle.basic.constant.datadict.WxMaterialType;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.basic.security.SecurityContext;
import in.hocg.eagle.modules.wx.entity.WxMaterial;
import in.hocg.eagle.modules.wx.manager.WxMpManager;
import in.hocg.eagle.modules.wx.mapper.WxMaterialMapper;
import in.hocg.eagle.modules.wx.mapstruct.WxMaterialMapping;
import in.hocg.eagle.modules.wx.pojo.qo.material.*;
import in.hocg.eagle.modules.wx.pojo.vo.material.WxMaterialComplexVo;
import in.hocg.eagle.modules.wx.service.WxMaterialService;
import in.hocg.eagle.utils.ValidUtils;
import in.hocg.eagle.utils.file.FileUtils;
import in.hocg.eagle.utils.string.JsonUtils;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class)
    public void uploadVoice(WxMaterialUploadVoiceQo qo) {
        final String appid = qo.getAppid();
        final String url = qo.getUrl();
        this.uploadFile(appid, WxMaterialType.Voice.getCode(), url);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadImage(WxMaterialUploadImageQo qo) {
        final String appid = qo.getAppid();
        final String url = qo.getUrl();
        this.uploadFile(appid, WxMaterialType.Image.getCode(), url);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
    public void uploadNews(WxMaterialUploadNewsQo qo) {
        final String appid = qo.getAppid();
        final LocalDateTime createdAt = qo.getCreatedAt();
        final Long userId = qo.getUserId();

        final List<WxMaterialType.News.NewsItem> items = qo.getNewsItems().stream()
            .map(newsItem -> this.convertNewsItem(appid, newsItem))
            .collect(Collectors.toList());

        final WxMpMaterialUploadResult result = wxMpManager.uploadMaterialNews(appid, items);

        final WxMaterial entity = new WxMaterial();
        entity.setAppid(appid);
        entity.setMaterialType(WxMaterialType.News.getCode());
        entity.setCreatedAt(createdAt);
        entity.setCreator(userId);
        entity.setMaterialContent(JsonUtils.toJSONString(new WxMaterialType.News(items)));
        entity.setMaterialResult(JsonUtils.toJSONString(
            new WxMaterialType.Result()
                .setMediaId(result.getMediaId())
                .setUrl(result.getUrl())
        ));
        validInsertOrUpdate(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateNews(WxMaterialUpdateNewsQo qo) {
        final Long id = qo.getId();
        final LocalDateTime createdAt = qo.getCreatedAt();
        final Long userId = qo.getUserId();
        final Integer index = qo.getIndex();
        final WxMaterialUploadNewsQo.NewsItem item = qo.getItem();

        final WxMaterial entity = getById(id);
        ValidUtils.notNull(entity, "图文不存在");
        final WxMaterialType materialType = IntEnum.ofThrow(entity.getMaterialType(), WxMaterialType.class);
        final WxMaterialType.News news = materialType.asContent(entity.getMaterialContent());
        final WxMaterialType.Result result = materialType.asResult(entity.getMaterialResult());
        ValidUtils.isTrue(news.size() >= index, "图文下标错误");
        final String appid = entity.getAppid();
        final WxMaterialType.News.NewsItem updateItem = this.convertNewsItem(appid, item);
        final boolean bool = wxMpManager.updateMaterialNews(appid, result.getMediaId(), index, updateItem);
        ValidUtils.isTrue(bool, "更新失败");
        news.set(index, updateItem);

        final WxMaterial updated = new WxMaterial();
        updated.setId(id);
        updated.setLastUpdatedAt(createdAt);
        updated.setLastUpdater(userId);
        updated.setMaterialContent(JsonUtils.toJSONString(new WxMaterialType.News(news)));
        validInsertOrUpdate(updated);
    }

    private WxMaterialType.News.NewsItem convertNewsItem(String appid, WxMaterialUploadNewsQo.NewsItem item) {
        final WxMaterialType.News.NewsItem result = mapping.asWxMaterialType0News0NewsItem(item);
        final String thumbMediaId = item.getThumbMediaId();
        if (Strings.isNotBlank(thumbMediaId)) {
            result.setThumbMediaId(thumbMediaId);
        } else {
            final WxMaterialType materialType = WxMaterialType.Image;
            final WxMaterial wxMaterial = this.uploadFile(appid, materialType.getCode(), item.getOriginalUrl());
            WxMaterialType.Result imageResult = materialType.asResult(wxMaterial.getMaterialResult());
            result.setThumbMediaId(imageResult.getMediaId());
        }
        return result;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<WxMaterialComplexVo> paging(WxMaterialPageQo qo) {
        final IPage<WxMaterial> paging = baseMapper.paging(qo, qo.page());
        return paging.convert(this::convertComplex);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WxMaterialComplexVo selectOne(IdQo qo) {
        final WxMaterial entity = getById(qo.getId());
        return this.convertComplex(entity);
    }

    private WxMaterialComplexVo convertComplex(WxMaterial entity) {
        final WxMaterialComplexVo result = mapping.asWxMaterialComplex(entity);
        final WxMaterialType materialType = IntEnum.ofThrow(entity.getMaterialType(), WxMaterialType.class);
        result.setMaterialContent(materialType.asContent(entity.getMaterialContent()));
        result.setMaterialResult(materialType.asResult(entity.getMaterialResult()));
        return result;
    }

    /**
     * 非图文/非视频
     */
    private WxMaterial uploadFile(String appid, Integer materialType, String url) {
        final LocalDateTime createdAt = LocalDateTime.now();
        final Long userId = SecurityContext.getCurrentUserId();
        final WxMaterialType wxMaterialType = IntEnum.ofThrow(materialType, WxMaterialType.class);
        final String mediaFileType = wxMaterialType.asMediaFileType();

        File file = null;
        WxMpMaterialUploadResult result = null;
        try {
            file = FileUtils.toFile(new URL(url));
            result = wxMpManager.uploadMaterialFile(appid, mediaFileType, file);
        } catch (IOException e) {
            e.printStackTrace();
            ValidUtils.fail(e.getMessage());
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
        return entity;
    }
}
