package in.hocg.eagle.mapstruct;

import in.hocg.eagle.modules.pms.entity.ProductCategory;
import in.hocg.eagle.modules.pms.pojo.qo.category.ProductCategorySaveQo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/3/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface ProductCategoryMapping {

    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "treePath", ignore = true)
    @Mapping(target = "creator", ignore = true)
    ProductCategory asProductCategory(ProductCategorySaveQo qo);
}
