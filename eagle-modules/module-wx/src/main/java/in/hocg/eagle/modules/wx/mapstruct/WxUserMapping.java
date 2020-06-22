package in.hocg.eagle.modules.wx.mapstruct;

import in.hocg.eagle.modules.wx.entity.WxUser;
import in.hocg.eagle.modules.wx.pojo.vo.user.WxMpUserComplexVo;
import org.mapstruct.Mapper;

/**
 * Created by hocgin on 2020/5/10.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface WxUserMapping {

    WxMpUserComplexVo asWxMpUserComplex(WxUser entity);
}
