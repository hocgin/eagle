package in.hocg.eagle.modules.pms.api;

import in.hocg.eagle.modules.pms.api.vo.SkuComplexVo;

/**
 * Created by hocgin on 2020/6/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface SkuAPI {
    SkuComplexVo selectOne(Long skuId);

    boolean casValidAndSubtractStock(Long skuId, Integer quantity);

    boolean casValidAndPlusStock(Long skuId, Integer quantity);
}
