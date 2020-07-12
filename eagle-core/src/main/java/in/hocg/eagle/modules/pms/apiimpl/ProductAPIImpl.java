package in.hocg.eagle.modules.pms.apiimpl;

import in.hocg.eagle.modules.pms.api.ProductAPI;
import in.hocg.eagle.modules.pms.api.vo.ProductComplexVo;
import in.hocg.eagle.modules.pms.service.ProductService;
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
public class ProductAPIImpl implements ProductAPI {
    private final ProductService service;

    @Override
    public ProductComplexVo selectOne(Long productId) {
        return service.selectOne(productId);
    }
}
