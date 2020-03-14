package in.hocg.eagle.mapstruct;

import in.hocg.eagle.modules.shop.entity.Sku;
import in.hocg.eagle.modules.shop.pojo.qo.ProductSaveQo;
import in.hocg.eagle.modules.shop.pojo.vo.sku.SkuComplexVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/3/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface SkuMapping {
    @Mapping(target = "spec", ignore = true)
    SkuComplexVo asSkuComplexVo(Sku sku);

    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "sale", ignore = true)
    @Mapping(target = "specData", ignore = true)
    Sku asSku(ProductSaveQo.Sku sku);
}
