package in.hocg.eagle.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractServiceImpl;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.modules.wx.entity.WxMpShortUrl;
import in.hocg.eagle.modules.wx.manager.WxMpManager;
import in.hocg.eagle.modules.wx.mapper.WxMpShortUrlMapper;
import in.hocg.eagle.modules.wx.mapstruct.WxMpShortUrlMapping;
import in.hocg.eagle.modules.wx.pojo.qo.shorturl.WxMpShortUrlInsertQo;
import in.hocg.eagle.modules.wx.pojo.qo.shorturl.WxMpShortUrlPageQo;
import in.hocg.eagle.modules.wx.pojo.vo.shorturl.WxMpShortUrlComplexVo;
import in.hocg.eagle.modules.wx.service.WxMpShortUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * [微信模块] 短链接表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-05-24
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class WxMpShortUrlServiceImpl extends AbstractServiceImpl<WxMpShortUrlMapper, WxMpShortUrl> implements WxMpShortUrlService {
    private final WxMpManager wxMpManager;
    private final WxMpShortUrlMapping mapping;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOne(WxMpShortUrlInsertQo qo) {
        final String appid = qo.getAppid();
        final String longUrl = qo.getLongUrl();
        final LocalDateTime createdAt = qo.getCreatedAt();
        final Long userId = qo.getUserId();
        final String shortUrl = wxMpManager.shortUrl(appid, longUrl);
        baseMapper.insert(new WxMpShortUrl()
            .setAppid(appid)
            .setLongUrl(longUrl)
            .setShortUrl(shortUrl)
            .setCreator(userId)
            .setCreatedAt(createdAt));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<WxMpShortUrlComplexVo> paging(WxMpShortUrlPageQo qo) {
        return baseMapper.paging(qo, qo.page())
            .convert(this::convertComplex);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WxMpShortUrlComplexVo selectOne(IdRo qo) {
        return this.convertComplex(getById(qo.getId()));
    }

    private WxMpShortUrlComplexVo convertComplex(WxMpShortUrl entity) {
        return mapping.asWxMpShortUrlComplex(entity);
    }
}
