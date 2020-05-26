package in.hocg.eagle.modules.wx.mapstruct;

import in.hocg.eagle.modules.wx.entity.WxMpReplyRule;
import in.hocg.eagle.modules.wx.pojo.qo.reply.WxReplyRuleSaveQo;
import in.hocg.eagle.modules.wx.pojo.qo.reply.WxReplyRuleComplexVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/5/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface WxMpReplyRuleMapping {
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "matchRule", ignore = true)
    @Mapping(target = "replyContent", ignore = true)
    WxMpReplyRule asWxMpReplyRule(WxReplyRuleSaveQo qo);

    @Mapping(target = "lastUpdaterName", ignore = true)
    @Mapping(target = "creatorName", ignore = true)
    WxReplyRuleComplexVo asWxReplyRuleComplexVo(WxMpReplyRule entity);

}
