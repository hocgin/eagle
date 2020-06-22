package in.hocg.eagle.modules.wx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.wx.entity.WxMaterial;
import in.hocg.eagle.modules.wx.pojo.qo.material.WxMaterialPageQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [微信模块] 微信素材库 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-05-05
 */
@Mapper
public interface WxMaterialMapper extends BaseMapper<WxMaterial> {

    IPage<WxMaterial> paging(@Param("qo") WxMaterialPageQo qo, @Param("page") Page page);
}
