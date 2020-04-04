package in.hocg.eagle.modules.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import in.hocg.eagle.modules.pms.entity.ProductCategory;
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

    /**
     * 商品品类是否正在被商品使用
     *
     * @param regexTreePath
     * @return
     */
    Integer countUsedProduct(@Param("regexTreePath") String regexTreePath);

    /**
     * 商品品类是否正在被优惠券使用
     *
     * @param regexTreePath
     * @return
     */
    Integer countUsedCoupon(@Param("regexTreePath") String regexTreePath);
}
