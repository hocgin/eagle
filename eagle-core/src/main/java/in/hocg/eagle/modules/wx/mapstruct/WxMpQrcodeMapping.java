package in.hocg.eagle.modules.wx.mapstruct;

import in.hocg.eagle.modules.wx.entity.WxMpQrcode;
import in.hocg.eagle.modules.wx.pojo.qo.qrcode.WxMpQrcodeInsertQo;
import in.hocg.eagle.modules.wx.pojo.vo.qrcode.WxMpQrcodeComplexVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/5/24.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface WxMpQrcodeMapping {

    @Mapping(target = "url", ignore = true)
    @Mapping(target = "ticket", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creator", ignore = true)
    WxMpQrcode asWxMpQrcode(WxMpQrcodeInsertQo qo);

    WxMpQrcodeComplexVo asWxMpQrcodeComplex(WxMpQrcode entity);
}
