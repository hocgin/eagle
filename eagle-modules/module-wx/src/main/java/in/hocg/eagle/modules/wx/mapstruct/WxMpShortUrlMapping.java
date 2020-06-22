package in.hocg.eagle.modules.wx.mapstruct;

import in.hocg.eagle.modules.wx.entity.WxMpShortUrl;
import in.hocg.eagle.modules.wx.pojo.vo.shorturl.WxMpShortUrlComplexVo;
import org.mapstruct.Mapper;

/**
 * Created by hocgin on 2020/5/24.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface WxMpShortUrlMapping {

    WxMpShortUrlComplexVo asWxMpShortUrlComplex(WxMpShortUrl entity);
}
