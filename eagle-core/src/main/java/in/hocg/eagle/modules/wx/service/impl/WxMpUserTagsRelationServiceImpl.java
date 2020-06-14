package in.hocg.eagle.modules.wx.service.impl;

import com.google.common.collect.Lists;
import in.hocg.web.AbstractServiceImpl;
import in.hocg.web.exception.ServiceException;
import in.hocg.eagle.modules.wx.entity.WxMpUserTags;
import in.hocg.eagle.modules.wx.entity.WxMpUserTagsRelation;
import in.hocg.eagle.modules.wx.entity.WxUser;
import in.hocg.eagle.modules.wx.manager.WxMpManager;
import in.hocg.eagle.modules.wx.mapper.WxMpUserTagsRelationMapper;
import in.hocg.eagle.modules.wx.service.WxMpUserTagsRelationService;
import in.hocg.eagle.modules.wx.service.WxMpUserTagsService;
import in.hocg.eagle.modules.wx.service.WxUserService;
import in.hocg.web.utils.LangUtils;
import in.hocg.web.utils.ValidUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * <p>
 * [微信模块] 标签x用户表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-05-19
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class WxMpUserTagsRelationServiceImpl extends AbstractServiceImpl<WxMpUserTagsRelationMapper, WxMpUserTagsRelation> implements WxMpUserTagsRelationService {
    private final WxMpUserTagsService wxMpUserTagsService;
    private final WxUserService wxUserService;
    private final WxMpManager wxMpManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchInsertIfNotExist(@NonNull Long tagsId, List<WxMpUserTagsRelation> pullData) {
        final List<WxMpUserTagsRelation> allData = selectListByTagsId(tagsId);

        final BiFunction<WxMpUserTagsRelation, WxMpUserTagsRelation, Boolean> isSame = (t1, t2) -> LangUtils.equals(t1.getTagsId(), t2.getTagsId()) && LangUtils.equals(t1.getOpenid(), t2.getOpenid());
        final List<WxMpUserTagsRelation> mixedList = LangUtils.getMixed(allData, pullData, isSame);
        List<WxMpUserTagsRelation> deleteList = LangUtils.removeIfExits(allData, mixedList, isSame);
        List<WxMpUserTagsRelation> addList = LangUtils.removeIfExits(pullData, mixedList, isSame);

        // 删除
        this.removeByIds(deleteList.parallelStream().map(WxMpUserTagsRelation::getId).filter(Objects::nonNull).collect(Collectors.toList()));

        // 新增
        addList.parallelStream().forEach(this::validInsertOrUpdate);

        // 更新
        mixedList.parallelStream().forEach(this::validInsertOrUpdate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    public void setTagWithinUser(Long tagsId, List<Long> wxUserId) {
        final WxMpUserTags entity = wxMpUserTagsService.getById(tagsId);
        ValidUtils.notNull(entity, "标签不存在");
        final List<WxUser> users = wxUserService.selectListById(wxUserId);
        ValidUtils.isFalse(users.isEmpty(), "微信用户不存在");
        final Long tagId = entity.getTagId();
        ValidUtils.notNull(tagId, "微信标签错误");
        final String tagAppid = entity.getAppid();
        if (users.parallelStream().anyMatch(wxUser -> !LangUtils.equals(wxUser.getAppid(), tagAppid))) {
            throw ServiceException.wrap("非同一公众号");
        }

        // 保存数据
        for (WxUser user : users) {
            final String openid = user.getOpenid();
            final String appid = user.getAppid();
            wxMpManager.setUserTag(appid, tagId, Lists.newArrayList(openid));
            validInsert(new WxMpUserTagsRelation()
                .setTagsId(tagsId)
                .setOpenid(openid));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    public void unsetTagWithinUser(Long tagsId, List<Long> wxUserId) {
        final WxMpUserTags entity = wxMpUserTagsService.getById(tagsId);
        ValidUtils.notNull(entity, "标签不存在");
        final List<WxUser> users = wxUserService.selectListById(wxUserId);
        ValidUtils.isFalse(users.isEmpty(), "微信用户不存在");
        final Long tagId = entity.getTagId();
        ValidUtils.notNull(tagId, "微信标签错误");
        final String tagAppid = entity.getAppid();
        if (users.parallelStream().anyMatch(wxUser -> !LangUtils.equals(wxUser.getAppid(), tagAppid))) {
            throw ServiceException.wrap("非同一公众号");
        }

        // 删除数据
        for (WxUser user : users) {
            final String openid = user.getOpenid();
            final String appid = user.getAppid();
            wxMpManager.unsetUserTag(appid, tagId, Lists.newArrayList(openid));
            deleteByTagsIdAndOpenId(tagsId, openid);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByTagsId(Long id) {
        baseMapper.deleteByTagsId(id);
    }

    public List<WxMpUserTagsRelation> selectListByTagsId(Long tagsId) {
        return lambdaQuery().eq(WxMpUserTagsRelation::getTagsId, tagsId).list();
    }

    public void deleteByTagsIdAndOpenId(Long tagsId, String openId) {
        baseMapper.deleteByTagsIdAndOpenId(tagsId, openId);
    }
}
