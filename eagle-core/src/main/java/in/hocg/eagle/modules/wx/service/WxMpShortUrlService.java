package in.hocg.eagle.modules.wx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.modules.wx.entity.WxMpShortUrl;
import in.hocg.eagle.modules.wx.pojo.qo.shorturl.WxMpShortUrlInsertQo;
import in.hocg.eagle.modules.wx.pojo.qo.shorturl.WxMpShortUrlPageQo;
import in.hocg.eagle.modules.wx.pojo.vo.shorturl.WxMpShortUrlComplexVo;

/**
 * <p>
 * [微信模块] 短链接表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-05-24
 */
public interface WxMpShortUrlService extends AbstractService<WxMpShortUrl> {

    void insertOne(WxMpShortUrlInsertQo qo);

    IPage<WxMpShortUrlComplexVo> paging(WxMpShortUrlPageQo qo);

    WxMpShortUrlComplexVo selectOne(IdQo qo);
}
