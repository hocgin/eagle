package in.hocg.eagle.modules.pms.mapper;

import in.hocg.eagle.modules.pms.entity.ProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import in.hocg.eagle.modules.pms.pojo.qo.category.ProductCategorySearchQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [Shop模块] 商品品类表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-03-14
 */
@Mapper
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {

    List<ProductCategory> search(@Param("qo") ProductCategorySearchQo qo);
}
