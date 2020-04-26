package in.hocg.eagle.modules.wx.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.modules.wx.entity.WxUser;
import in.hocg.eagle.modules.wx.mapper.WxUserMapper;
import in.hocg.eagle.modules.wx.service.WxUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
}
