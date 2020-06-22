package in.hocg.eagle.modules.wx.mapstruct;

import in.hocg.eagle.modules.wx.entity.WxMpUserTags;
import in.hocg.eagle.modules.wx.pojo.vo.user.tags.WxMpUserTagsComplexVo;
import org.mapstruct.Mapper;

/**
 * Created by hocgin on 2020/5/19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface WxMpUserTagsMapping {

    WxMpUserTagsComplexVo asWxMpUserTagsComplex(WxMpUserTags entity);
}
