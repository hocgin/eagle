package in.hocg.eagle.modules.wx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractService;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.constant.datadict.WxMatchMsgType;
import in.hocg.eagle.modules.wx.entity.WxMpReplyRule;
import in.hocg.eagle.modules.wx.pojo.qo.reply.WxReplyRulePageQo;
import in.hocg.eagle.modules.wx.pojo.qo.reply.WxReplyRuleSaveQo;
import in.hocg.eagle.modules.wx.pojo.qo.reply.WxReplyRuleComplexVo;

import java.util.List;

/**
 * <p>
 * 微信自动回复配置表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-05-15
 */
public interface WxMpReplyRuleService extends AbstractService<WxMpReplyRule> {

    void insertOne(WxReplyRuleSaveQo qo);

    void updateOne(WxReplyRuleSaveQo qo);

    IPage<WxReplyRuleComplexVo> paging(WxReplyRulePageQo qo);

    WxReplyRuleComplexVo selectOne(Long id);

    List<WxMpReplyRule> selectListByAppidAndMatchMsgTypeAndEnabledSortedDesc(String appId, WxMatchMsgType wxMatchMsgType, Enabled enabled);
}
