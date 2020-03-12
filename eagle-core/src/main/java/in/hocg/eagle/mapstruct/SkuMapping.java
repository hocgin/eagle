package in.hocg.eagle.mapstruct;

import in.hocg.eagle.modules.shop.entity.Sku;
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
    @Mapping(target = "specValue", ignore = true)
    @Mapping(target = "specValueId", ignore = true)
    SkuComplexVo asSkuComplexVo(Sku sku);
}
