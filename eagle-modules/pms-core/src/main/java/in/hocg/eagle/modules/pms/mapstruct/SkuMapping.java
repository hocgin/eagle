package in.hocg.eagle.modules.pms.mapstruct;

import in.hocg.eagle.modules.oms.pojo.dto.order.OrderItemDto;
import in.hocg.eagle.modules.pms.entity.Product;
import in.hocg.eagle.modules.pms.entity.Sku;
import in.hocg.eagle.modules.pms.pojo.qo.ProductSaveQo;
import in.hocg.eagle.modules.pms.pojo.vo.sku.SkuComplexVo;
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
    SkuComplexVo asSkuComplexVo(Sku entity);

    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "sale", ignore = true)
    @Mapping(target = "specData", ignore = true)
    Sku asSku(ProductSaveQo.Sku entity);

    @Mapping(target = "productCategoryId", source = "product.productCategoryId")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productPrice", source = "entity.price")
    @Mapping(target = "productPic", source = "entity.imageUrl")
    @Mapping(target = "productSkuCode", source = "entity.skuCode")
    @Mapping(target = "productSkuId", source = "entity.id")
    @Mapping(target = "productSpecData", source = "entity.specData")
    @Mapping(target = "productName", source = "product.title")
    @Mapping(target = "productQuantity", ignore = true)
    @Mapping(target = "couponAmount", ignore = true)
    @Mapping(target = "realAmount", ignore = true)
    OrderItemDto asOrderItemDto(Sku entity, Product product);
}
