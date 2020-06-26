package in.hocg.eagle.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import in.hocg.eagle.basic.ext.mybatis.basic.AbstractServiceImpl;
import in.hocg.eagle.modules.wx.entity.WxMpUserTags;
import in.hocg.eagle.modules.wx.entity.WxMpUserTagsRelation;
import in.hocg.eagle.modules.wx.manager.WxMpManager;
import in.hocg.eagle.modules.wx.mapper.WxMpUserTagsMapper;
import in.hocg.eagle.modules.wx.mapstruct.WxMpUserTagsMapping;
import in.hocg.eagle.modules.wx.pojo.qo.user.tags.*;
import in.hocg.eagle.modules.wx.pojo.vo.user.tags.WxMpUserTagsComplexVo;
import in.hocg.eagle.modules.wx.service.WxMpUserTagsRelationService;
import in.hocg.eagle.modules.wx.service.WxMpUserTagsService;
import in.hocg.eagle.modules.wx.service.WxUserService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * <p>
 * [微信模块] 用户标签表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-05-19
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class WxMpUserTagsServiceImpl extends AbstractServiceImpl<WxMpUserTagsMapper, WxMpUserTags> implements WxMpUserTagsService {
    private final WxMpManager wxMpManager;
    private final WxMpUserTagsMapping mapping;
    private final WxMpUserTagsRelationService wxMpUserTagsRelationService;
    private final WxUserService wxUserService;

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refresh(WxMpUserTagsRefreshQo qo) {
        final String appid = qo.getAppid();

        List<WxMpUserTags> pullData = Lists.newArrayList();
        wxMpManager.getTags(appid, pullData::add);
        final List<WxMpUserTags> allData = this.selectListByAppid(appid);

        final BiFunction<WxMpUserTags, WxMpUserTags, Boolean> isSame = (t1, t2) -> LangUtils.equals(t1.getAppid(), t2.getAppid()) && LangUtils.equals(t1.getTagId(), t2.getTagId());
        final List<WxMpUserTags> mixedList = LangUtils.getMixed(allData, pullData, isSame);
        List<WxMpUserTags> deleteList = LangUtils.removeIfExits(allData, mixedList, isSame);
        List<WxMpUserTags> addList = LangUtils.removeIfExits(pullData, mixedList, isSame);

        // 删除
        deleteList.parallelStream().map(WxMpUserTags::getId)
            .filter(Objects::nonNull).forEach(this::deleteOne);

        // 新增
        addList.parallelStream().forEach(this::validInsertOrUpdate);

        // 更新
        mixedList.parallelStream().forEach(this::validInsertOrUpdate);

        final List<WxMpUserTags> all = Lists.newArrayList();
        all.addAll(addList);
        all.addAll(mixedList);

        for (WxMpUserTags item : all) {
            final Long tagsId = item.getId();
            final Long tagId = item.getTagId();
            List<WxMpUserTagsRelation> userTags = Lists.newArrayList();
            wxMpManager.getTagUsers(appid, tagId, null, openId -> userTags.add(new WxMpUserTagsRelation().setOpenid(openId).setTagsId(tagsId)));
            wxMpUserTagsRelationService.batchInsertIfNotExist(tagsId, userTags);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOne(WxMpUserTagsInsertQo qo) {
        final String appid = qo.getAppid();
        final String name = qo.getName();
        final WxMpUserTags tag = wxMpManager.createTag(appid, name);
        this.validInsert(tag);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<WxMpUserTagsComplexVo> paging(WxMpUserTagsPageQo qo) {
        return baseMapper.paging(qo, qo.page())
            .convert(this::convertComplex);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setTagWithinUser(WxMpSetUserTagsQo qo) {
        final Long tagsId = qo.getTagsId();
        final List<Long> wxUserId = qo.getWxUserId();
        // 不回滚
        wxMpUserTagsRelationService.setTagWithinUser(tagsId, wxUserId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unsetTagWithinUser(WxMpUnsetUserTagsQo qo) {
        final Long tagsId = qo.getTagsId();
        final List<Long> wxUserId = qo.getWxUserId();
        // 不回滚
        wxMpUserTagsRelationService.unsetTagWithinUser(tagsId, wxUserId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOne(Long tagsId) {
        final WxMpUserTags entity = getById(tagsId);
        ValidUtils.notNull(entity, "数据不存在");
        baseMapper.deleteById(tagsId);
        wxMpUserTagsRelationService.deleteByTagsId(tagsId);
        wxMpManager.deleteTag(entity.getAppid(), tagsId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WxMpUserTagsComplexVo selectOne(Long id) {
        final WxMpUserTags entity = getById(id);
        return this.convertComplex(entity);
    }

    private WxMpUserTagsComplexVo convertComplex(WxMpUserTags entity) {
        return mapping.asWxMpUserTagsComplex(entity);
    }

    private WxMpUserTags getOrCreate(WxMpUserTags entity) {
        final String appid = entity.getAppid();
        final Long tagId = entity.getTagId();
        ValidUtils.notNull(appid);
        ValidUtils.notNull(tagId);
        final Optional<WxMpUserTags> tagOpt = this.selectOneByAppidAndTagId(appid, tagId);
        if (tagOpt.isPresent()) {
            return tagOpt.get();
        }
        validInsert(entity);
        return entity;
    }

    private Optional<WxMpUserTags> selectOneByAppidAndTagId(String appid, Long tagId) {
        return lambdaQuery().eq(WxMpUserTags::getAppid, appid).eq(WxMpUserTags::getTagId, tagId).oneOpt();
    }

    private List<WxMpUserTags> selectListByAppid(String appid) {
        return lambdaQuery().eq(WxMpUserTags::getAppid, appid).list();
    }
}
