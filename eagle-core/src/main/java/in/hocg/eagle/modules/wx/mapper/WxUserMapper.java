package in.hocg.eagle.modules.wx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.wx.entity.WxUser;
import in.hocg.eagle.modules.wx.pojo.qo.user.WxMpUserPagingQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 微信用户表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-04-25
 */
@Mapper
public interface WxUserMapper extends BaseMapper<WxUser> {

    IPage<WxUser> paging(@Param("qo") WxMpUserPagingQo qo, @Param("page") Page page);
}
