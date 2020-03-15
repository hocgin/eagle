package in.hocg.eagle.modules.shop.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.shop.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import in.hocg.eagle.modules.shop.pojo.qo.ProductPagingQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [Shop模块] 商品表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-03-10
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    IPage<Product> paging(@Param("qo") ProductPagingQo qo, @Param("page") Page page);

}
