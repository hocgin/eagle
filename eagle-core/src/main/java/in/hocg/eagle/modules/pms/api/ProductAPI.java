package in.hocg.eagle.modules.pms.api;

import in.hocg.eagle.modules.pms.api.vo.ProductComplexVo;

/**
 * Created by hocgin on 2020/6/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface ProductAPI {
    ProductComplexVo selectOne(Long productId);
}
