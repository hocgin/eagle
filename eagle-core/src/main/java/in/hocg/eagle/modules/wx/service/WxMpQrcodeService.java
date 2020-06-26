package in.hocg.eagle.modules.wx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.ext.mybatis.basic.AbstractService;
import in.hocg.eagle.basic.pojo.ro.IdRo;
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

    WxMpQrcodeComplexVo selectOne(IdRo qo);

    IPage<WxMpQrcodeComplexVo> paging(WxMpQrcodePageQo qo);

}
