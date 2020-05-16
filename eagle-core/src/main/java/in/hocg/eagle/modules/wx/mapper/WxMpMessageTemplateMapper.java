package in.hocg.eagle.modules.wx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.wx.entity.WxMpMessageTemplate;
import in.hocg.eagle.modules.wx.pojo.qo.message.template.WxMpMessageTemplatePageQo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [微信模块] 消息模版表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-05-16
 */
@Mapper
public interface WxMpMessageTemplateMapper extends BaseMapper<WxMpMessageTemplate> {

    IPage<WxMpMessageTemplate> paging(WxMpMessageTemplatePageQo qo, Page page);
}
