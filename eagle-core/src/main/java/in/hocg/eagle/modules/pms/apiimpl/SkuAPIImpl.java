package in.hocg.eagle.modules.pms.apiimpl;

import in.hocg.eagle.modules.pms.api.SkuAPI;
import in.hocg.eagle.modules.pms.api.vo.SkuComplexVo;
import in.hocg.eagle.modules.pms.service.SkuService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Created by hocgin on 2020/6/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SkuAPIImpl implements SkuAPI {
    private final SkuService service;

    @Override
    public SkuComplexVo selectOne(Long skuId) {
        return service.selectOne(skuId);
    }

    @Override
    public boolean casValidAndSubtractStock(Long skuId, Integer quantity) {
        return service.casValidAndSubtractStock(skuId, quantity);
    }

    @Override
    public boolean casValidAndPlusStock(Long skuId, Integer quantity) {
        return service.casValidAndPlusStock(skuId, quantity);
    }
}
