package in.hocg.eagle.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.modules.wx.entity.WxMpQrcode;
import in.hocg.eagle.modules.wx.manager.WxMpManager;
import in.hocg.eagle.modules.wx.mapper.WxMpQrcodeMapper;
import in.hocg.eagle.modules.wx.mapstruct.WxMpQrcodeMapping;
import in.hocg.eagle.modules.wx.pojo.qo.qrcode.WxMpQrcodeInsertQo;
import in.hocg.eagle.modules.wx.pojo.qo.qrcode.WxMpQrcodePageQo;
import in.hocg.eagle.modules.wx.pojo.vo.qrcode.WxMpQrcodeComplexVo;
import in.hocg.eagle.modules.wx.service.WxMpQrcodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * [微信模块] 二维码表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-05-24
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class WxMpQrcodeServiceImpl extends AbstractServiceImpl<WxMpQrcodeMapper, WxMpQrcode> implements WxMpQrcodeService {
    private final WxMpQrcodeMapping mapping;
    private final WxMpManager wxMpManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOne(WxMpQrcodeInsertQo qo) {
        final String appid = qo.getAppid();
        final Long userId = qo.getUserId();
        final LocalDateTime createdAt = qo.getCreatedAt();

        WxMpQrcode entity = mapping.asWxMpQrcode(qo);
        entity.setCreator(userId);
        entity.setCreatedAt(createdAt);
        this.validInsert(entity);

        WxMpQrcode updated;
        if (entity.getExpireSeconds() < 0) {
            updated = wxMpManager.createForeverQrCode(appid, entity.getSceneId(), entity.getSceneStr());
        } else {
            updated = wxMpManager.createTmpQrCode(appid, entity.getExpireSeconds(), entity.getSceneId(), entity.getSceneStr());
        }
        updated.setId(entity.getId());
        this.validUpdateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WxMpQrcodeComplexVo selectOne(IdQo qo) {
        final Long id = qo.getId();
        return this.convertComplex(getById(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<WxMpQrcodeComplexVo> paging(WxMpQrcodePageQo qo) {
        return baseMapper.paging(qo, qo.page())
            .convert(this::convertComplex);
    }

    private WxMpQrcodeComplexVo convertComplex(WxMpQrcode entity) {
        return mapping.asWxMpQrcodeComplex(entity);
    }
}
