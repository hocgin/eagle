package in.hocg.eagle.modules.wx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.web.AbstractService;
import in.hocg.web.pojo.qo.IdQo;
import in.hocg.eagle.modules.wx.entity.WxMpQrcode;
import in.hocg.eagle.modules.wx.pojo.qo.qrcode.WxMpQrcodeInsertQo;
import in.hocg.eagle.modules.wx.pojo.qo.qrcode.WxMpQrcodePageQo;
import in.hocg.eagle.modules.wx.pojo.vo.qrcode.WxMpQrcodeComplexVo;

/**
 * <p>
 * [微信模块] 二维码表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-05-24
 */
public interface WxMpQrcodeService extends AbstractService<WxMpQrcode> {

    void insertOne(WxMpQrcodeInsertQo qo);

    WxMpQrcodeComplexVo selectOne(IdQo qo);

    IPage<WxMpQrcodeComplexVo> paging(WxMpQrcodePageQo qo);

}
