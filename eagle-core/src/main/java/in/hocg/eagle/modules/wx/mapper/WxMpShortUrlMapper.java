package in.hocg.eagle.modules.wx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.wx.entity.WxMpShortUrl;
import in.hocg.eagle.modules.wx.pojo.qo.shorturl.WxMpShortUrlPageQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [微信模块] 短链接表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-05-24
 */
@Mapper
public interface WxMpShortUrlMapper extends BaseMapper<WxMpShortUrl> {

    IPage<WxMpShortUrl> paging(@Param("qo") WxMpShortUrlPageQo qo, @Param("page") Page page);
}
