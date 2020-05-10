package in.hocg.eagle.modules.wx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.wx.entity.WxMpConfig;
import in.hocg.eagle.modules.wx.pojo.qo.WxMpConfigPagingQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 微信公众号配置表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-04-25
 */
@Mapper
public interface WxMpConfigMapper extends BaseMapper<WxMpConfig> {

    IPage<WxMpConfig> paging(@Param("qo") WxMpConfigPagingQo qo, @Param("page") Page page);

    List<WxMpConfig> selectListByEnabled(@Param("enabled") Integer enabled);

    List<WxMpConfig> search();

}
