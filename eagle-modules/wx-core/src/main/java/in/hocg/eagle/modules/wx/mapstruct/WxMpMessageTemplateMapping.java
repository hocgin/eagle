package in.hocg.eagle.modules.wx.mapstruct;

import in.hocg.eagle.modules.wx.entity.WxMpMessageTemplate;
import in.hocg.eagle.modules.wx.pojo.vo.message.template.WxMpMessageTemplateComplexVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/5/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface WxMpMessageTemplateMapping {

    @Mapping(target = "lastUpdaterName", ignore = true)
    @Mapping(target = "creatorName", ignore = true)
    WxMpMessageTemplateComplexVo asWxMpMessageTemplateComplexVo(WxMpMessageTemplate entity);

}
