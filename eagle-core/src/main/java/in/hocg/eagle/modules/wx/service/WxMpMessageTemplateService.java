package in.hocg.eagle.modules.wx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.modules.wx.entity.WxMpMessageTemplate;
import in.hocg.eagle.modules.wx.pojo.qo.message.template.RefreshMessageTemplateQo;
import in.hocg.eagle.modules.wx.pojo.qo.message.template.WxMpMessageTemplatePageQo;
import in.hocg.eagle.modules.wx.pojo.vo.message.template.WxMpMessageTemplateComplexVo;

/**
 * <p>
 * [微信模块] 消息模版表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-05-16
 */
public interface WxMpMessageTemplateService extends AbstractService<WxMpMessageTemplate> {

    void refresh(RefreshMessageTemplateQo qo);

    IPage<WxMpMessageTemplateComplexVo> paging(WxMpMessageTemplatePageQo qo);

    WxMpMessageTemplateComplexVo selectOne(Long id);
}
