package in.hocg.eagle.modules.pms.api;


import in.hocg.basic.api.vo.SkuComplexVo;

/**
 * Created by hocgin on 2020/6/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface SkuAPI {

    SkuComplexVo getById(Long skuId);

    boolean casValidAndPlusStock(Long skuId, Integer quantity);

    boolean casValidAndSubtractStock(Long skuId, Integer quantity);
}
