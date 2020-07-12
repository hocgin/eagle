package in.hocg.eagle.modules.pms.mapstruct;

import in.hocg.eagle.modules.pms.entity.Sku;
import in.hocg.eagle.modules.pms.pojo.qo.ProductSaveQo;
import in.hocg.eagle.modules.pms.api.vo.SkuComplexVo;
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
}
