package in.hocg.eagle.modules.shop.service.impl;

import in.hocg.eagle.modules.shop.entity.Product;
import in.hocg.eagle.modules.shop.mapper.ProductMapper;
import in.hocg.eagle.modules.shop.service.ProductService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * [Shop模块] 商品表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-10
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class ProductServiceImpl extends AbstractServiceImpl<ProductMapper, Product> implements ProductService {

}
