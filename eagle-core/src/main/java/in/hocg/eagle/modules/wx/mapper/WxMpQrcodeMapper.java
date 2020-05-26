package in.hocg.eagle.modules.wx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.wx.entity.WxMpQrcode;
import in.hocg.eagle.modules.wx.pojo.qo.qrcode.WxMpQrcodePageQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [微信模块] 二维码表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-05-24
 */
@Mapper
public interface WxMpQrcodeMapper extends BaseMapper<WxMpQrcode> {

    IPage<WxMpQrcode> paging(@Param("qo") WxMpQrcodePageQo qo, @Param("page") Page page);
}
