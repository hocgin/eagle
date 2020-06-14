package in.hocg.eagle.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.constant.CodeEnum;
import in.hocg.eagle.basic.constant.datadict.WxMatchMsgType;
import in.hocg.eagle.basic.constant.datadict.WxReplyMsgType;
import in.hocg.eagle.modules.wx.entity.WxMpReplyRule;
import in.hocg.eagle.modules.wx.mapper.WxMpReplyRuleMapper;
import in.hocg.eagle.modules.wx.mapstruct.WxMpReplyRuleMapping;
import in.hocg.eagle.modules.wx.pojo.qo.reply.WxReplyRulePageQo;
import in.hocg.eagle.modules.wx.pojo.qo.reply.WxReplyRuleSaveQo;
import in.hocg.eagle.modules.wx.pojo.qo.reply.WxReplyRuleComplexVo;
import in.hocg.eagle.modules.wx.service.WxMpReplyRuleService;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 微信自动回复配置表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-05-15
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class WxMpReplyRuleServiceImpl extends AbstractServiceImpl<WxMpReplyRuleMapper, WxMpReplyRule> implements WxMpReplyRuleService {
    private final WxMpReplyRuleMapping mapping;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOne(WxReplyRuleSaveQo qo) {
        saveOne(qo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(WxReplyRuleSaveQo qo) {
        saveOne(qo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<WxReplyRuleComplexVo> paging(WxReplyRulePageQo qo) {
        return baseMapper.paging(qo, qo.page())
            .convert(this::convertComplex);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WxReplyRuleComplexVo selectOne(Long id) {
        final WxMpReplyRule entity = getById(id);
        ValidUtils.notNull(entity, "数据不存在");
        return this.convertComplex(entity);
    }

    @Override
    public List<WxMpReplyRule> selectListByAppidAndMatchMsgTypeAndEnabledSortedDesc(String appId, WxMatchMsgType wxMatchMsgType, Enabled enabled) {
        return baseMapper.selectListByAppidAndMatchMsgTypeAndEnabled(appId, wxMatchMsgType.getCode(), enabled.getCode());
    }

    private void saveOne(WxReplyRuleSaveQo qo) {
        WxMpReplyRule entity = mapping.asWxMpReplyRule(qo);
        final LocalDateTime createdAt = qo.getCreatedAt();
        final Long userId = qo.getUserId();

        final String matchRuleString = qo.getMatchRuleString();
        final String replyContentString = qo.getReplyContentString();

        CodeEnum.ofThrow(entity.getMatchMsgType(), WxMatchMsgType.class)
            .asObject(matchRuleString).validThrow();
        CodeEnum.ofThrow(entity.getReplyMsgType(), WxReplyMsgType.class)
            .asObject(replyContentString).validThrow();

        entity.setReplyContent(replyContentString);
        entity.setMatchRule(matchRuleString);
        if (Objects.isNull(entity.getId())) {
            entity.setCreatedAt(createdAt);
            entity.setCreator(userId);
        } else {
            entity.setLastUpdatedAt(createdAt);
            entity.setLastUpdater(userId);
        }
        validInsertOrUpdate(entity);
    }

    private WxReplyRuleComplexVo convertComplex(WxMpReplyRule entity) {
        WxReplyRuleComplexVo result = mapping.asWxReplyRuleComplexVo(entity);
        final WxMatchMsgType matchMsgType = CodeEnum.ofThrow(result.getMatchMsgType(), WxMatchMsgType.class);
        final WxReplyMsgType replyMsgType = CodeEnum.ofThrow(result.getReplyMsgType(), WxReplyMsgType.class);
        result.setReplyContent(replyMsgType.asObject(entity.getReplyContent()));
        result.setMatchRule(matchMsgType.asObject(entity.getMatchRule()));
        return result;
    }
}
