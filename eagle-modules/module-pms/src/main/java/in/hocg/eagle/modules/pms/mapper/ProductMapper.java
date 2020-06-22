package in.hocg.eagle.modules.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.pms.entity.Product;
import in.hocg.eagle.modules.pms.pojo.qo.ProductCompleteQo;
import in.hocg.eagle.modules.pms.pojo.qo.ProductPagingQo;
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

    IPage<Product> pagingWithComplete(@Param("qo") ProductCompleteQo qo, @Param("page") Page page);
}
