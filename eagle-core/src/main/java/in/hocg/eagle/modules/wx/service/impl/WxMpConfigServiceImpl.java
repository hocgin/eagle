package in.hocg.eagle.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.web.AbstractServiceImpl;
import in.hocg.web.constant.datadict.Enabled;
import in.hocg.eagle.modules.wx.entity.WxMpConfig;
import in.hocg.eagle.modules.wx.manager.WxMpManager;
import in.hocg.eagle.modules.wx.mapper.WxMpConfigMapper;
import in.hocg.eagle.modules.wx.mapstruct.WxMpConfigMapping;
import in.hocg.eagle.modules.wx.pojo.qo.WxMpConfigPagingQo;
import in.hocg.eagle.modules.wx.pojo.qo.WxMpConfigSaveQo;
import in.hocg.eagle.modules.wx.pojo.vo.WxMpConfigComplexVo;
import in.hocg.eagle.modules.wx.service.WxMpConfigService;
import in.hocg.web.utils.LangUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 微信公众号配置表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-25
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class WxMpConfigServiceImpl extends AbstractServiceImpl<WxMpConfigMapper, WxMpConfig> implements WxMpConfigService {
    private final WxMpConfigMapping mapping;
    private final WxMpManager wxMpManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(WxMpConfigSaveQo qo) {
        final WxMpConfig entity = mapping.asWxMpConfig(qo);
        final WxMpConfig dbEntity = getById(entity.getAppid());

        if (Objects.isNull(dbEntity)) {
            entity.setCreatedAt(qo.getCreatedAt());
            entity.setCreator(qo.getUserId());
            validInsert(entity);
        } else {
            entity.setLastUpdatedAt(qo.getCreatedAt());
            entity.setLastUpdater(qo.getUserId());
            validUpdateById(entity);
        }

        final String pkVal = (String) entity.pkVal();
        if (!LangUtils.equals(Enabled.Off.getCode(), qo.getEnabled())) {
            wxMpManager.insertOrUpdateWithMpConfig(getById(pkVal));
        } else {
            wxMpManager.removeWithMpConfig(pkVal);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<WxMpConfigComplexVo> paging(WxMpConfigPagingQo qo) {
        return baseMapper.paging(qo, qo.page()).convert(this::convertComplex);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WxMpConfigComplexVo selectOne(String appid) {
        final WxMpConfig entity = baseMapper.selectById(appid);
        return convertComplex(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<WxMpConfig> selectListByEnabled(Integer enabled) {
        return baseMapper.selectListByEnabled(enabled);
    }

    private WxMpConfigComplexVo convertComplex(WxMpConfig entity) {
        return mapping.asWxMpConfigComplex(entity);
    }
}
