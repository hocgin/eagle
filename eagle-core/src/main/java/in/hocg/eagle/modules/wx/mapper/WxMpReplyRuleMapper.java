package in.hocg.eagle.modules.wx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.wx.entity.WxMpReplyRule;
import in.hocg.eagle.modules.wx.pojo.qo.reply.WxReplyRulePageQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 微信自动回复配置表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-05-15
 */
@Mapper
public interface WxMpReplyRuleMapper extends BaseMapper<WxMpReplyRule> {

    IPage<WxMpReplyRule> paging(@Param("qo") WxReplyRulePageQo qo, @Param("page") Page page);

    List<WxMpReplyRule> selectListByAppidAndMatchMsgTypeAndEnabled(@Param("appid") String appid, @Param("matchMsgType") Integer matchMsgType, @Param("enabled") Integer enabled);
}
