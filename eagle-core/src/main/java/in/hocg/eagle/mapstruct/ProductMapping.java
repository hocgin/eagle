package in.hocg.eagle.mapstruct;

import in.hocg.eagle.modules.pms.entity.Product;
import in.hocg.eagle.modules.pms.pojo.qo.ProductSaveQo;
import in.hocg.eagle.modules.pms.pojo.vo.product.ProductComplexVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/3/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface ProductMapping {

    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Product asProduct(ProductSaveQo qo);

    @Mapping(target = "productCategoryName", ignore = true)
    @Mapping(target = "creatorName", ignore = true)
    @Mapping(target = "lastUpdaterName", ignore = true)
    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "publishStatusName", ignore = true)
    @Mapping(target = "sku", ignore = true)
    ProductComplexVo asProductComplex(Product entity);

}
