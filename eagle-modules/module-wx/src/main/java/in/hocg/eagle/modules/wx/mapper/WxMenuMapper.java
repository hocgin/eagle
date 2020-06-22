package in.hocg.eagle.modules.wx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.wx.entity.WxMenu;
import in.hocg.eagle.modules.wx.pojo.qo.menu.WxMenuPagingQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [微信模块] 菜单表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-05-04
 */
@Mapper
public interface WxMenuMapper extends BaseMapper<WxMenu> {

    IPage<WxMenu> paging(@Param("qo") WxMenuPagingQo qo, @Param("page") Page page);
}
