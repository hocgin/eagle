package in.hocg.eagle.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.ext.mybatis.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.modules.wx.entity.WxUser;
import in.hocg.eagle.modules.wx.manager.WxMpManager;
import in.hocg.eagle.modules.wx.mapper.WxUserMapper;
import in.hocg.eagle.modules.wx.mapstruct.WxUserMapping;
import in.hocg.eagle.modules.wx.pojo.qo.user.WxMpUserPagingQo;
import in.hocg.eagle.modules.wx.pojo.qo.user.WxMpUserRefreshQo;
import in.hocg.eagle.modules.wx.pojo.qo.user.WxMpUserSearchQo;
import in.hocg.eagle.modules.wx.pojo.vo.user.WxMpUserComplexVo;
import in.hocg.eagle.modules.wx.service.WxUserService;
import in.hocg.eagle.utils.LangUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 微信用户表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-25
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class WxUserServiceImpl extends AbstractServiceImpl<WxUserMapper, WxUser> implements WxUserService {
    private final WxUserMapping mapping;
    private final WxMpManager wxMpManager;

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refresh(WxMpUserRefreshQo qo) {
        final String appid = qo.getAppid();
        wxMpManager.getWxUserList(appid, null,
            openId -> wxMpManager.getWxUser(appid, openId).ifPresent(this::saveOrUpdate)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdate(WxUser entity) {
        final String appid = entity.getAppid();
        final String openid = entity.getOpenid();
        final Optional<WxUser> wxUserOpt = this.selectOne(appid, openid);
        if (!wxUserOpt.isPresent()) {
            entity.setCreatedAt(LocalDateTime.now());
            return this.save(entity);
        }
        entity.setId(wxUserOpt.get().getId());
        return super.updateById(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Optional<WxUser> selectOne(String appid, String openid) {
        return lambdaQuery().eq(WxUser::getAppid, appid)
            .eq(WxUser::getOpenid, openid).oneOpt();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unsubscribe(String appid, String openid) {
        lambdaUpdate().eq(WxUser::getAppid, appid).eq(WxUser::getOpenid, openid)
            .set(WxUser::getSubscribe, Boolean.FALSE).update();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<WxMpUserComplexVo> paging(WxMpUserPagingQo qo) {
        return baseMapper.paging(qo, qo.page()).convert(this::convertComplex);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WxMpUserComplexVo selectOne(IdRo qo) {
        final Long id = qo.getId();
        final WxUser entity = getById(id);
        return this.convertComplex(entity);
    }

    @Override
    public List<WxMpUserComplexVo> complete(WxMpUserSearchQo qo) {
        return LangUtils.toList(baseMapper.complete(qo), this::convertComplex);
    }

    private WxMpUserComplexVo convertComplex(WxUser entity) {
        WxMpUserComplexVo result = mapping.asWxMpUserComplex(entity);
        return result;
    }
}
