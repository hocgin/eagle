package in.hocg.eagle.modules.shop.service.impl;

import in.hocg.eagle.modules.shop.entity.ProductClassify;
import in.hocg.eagle.modules.shop.mapper.ProductClassifyMapper;
import in.hocg.eagle.modules.shop.service.ProductClassifyService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * [Shop模块] 商品分类表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-10
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class ProductClassifyServiceImpl extends AbstractServiceImpl<ProductClassifyMapper, ProductClassify> implements ProductClassifyService {

}