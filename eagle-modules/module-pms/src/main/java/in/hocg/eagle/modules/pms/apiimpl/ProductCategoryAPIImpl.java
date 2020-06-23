package in.hocg.eagle.modules.pms.apiimpl;

import in.hocg.basic.api.vo.ProductCategoryComplexVo;
import in.hocg.eagle.modules.pms.api.ProductCategoryAPI;
import in.hocg.eagle.modules.pms.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Created by hocgin on 2020/6/23.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class ProductCategoryAPIImpl implements ProductCategoryAPI {
    private final ProductCategoryService service;

    @Override
    public ProductCategoryComplexVo selectOne(Long id) {
        return service.selectOne(id);
    }
}
