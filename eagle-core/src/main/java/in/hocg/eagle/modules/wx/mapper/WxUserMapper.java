package in.hocg.eagle.modules.wx.mapper;

import in.hocg.eagle.modules.wx.entity.WxUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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

}
