package in.hocg.eagle.modules.shop.service.impl;

import in.hocg.eagle.modules.shop.entity.Sku;
import in.hocg.eagle.modules.shop.mapper.SkuMapper;
import in.hocg.eagle.modules.shop.service.SkuService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * [Shop模块] 商品SKU表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-10
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SkuServiceImpl extends AbstractServiceImpl<SkuMapper, Sku> implements SkuService {

}
