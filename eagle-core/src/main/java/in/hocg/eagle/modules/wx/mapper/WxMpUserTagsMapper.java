package in.hocg.eagle.modules.wx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.wx.entity.WxMpUserTags;
import in.hocg.eagle.modules.wx.pojo.qo.user.tags.WxMpUserTagsPageQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [微信模块] 用户标签表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-05-19
 */
@Mapper
public interface WxMpUserTagsMapper extends BaseMapper<WxMpUserTags> {

    IPage<WxMpUserTags> paging(@Param("qo") WxMpUserTagsPageQo qo, @Param("page") Page page);
}
