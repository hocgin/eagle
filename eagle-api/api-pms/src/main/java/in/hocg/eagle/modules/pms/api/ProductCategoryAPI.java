package in.hocg.eagle.modules.pms.api;

import in.hocg.basic.api.vo.ProductCategoryComplexVo;

/**
 * Created by hocgin on 2020/6/23.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface ProductCategoryAPI {
    ProductCategoryComplexVo selectOne(Long id);
}
