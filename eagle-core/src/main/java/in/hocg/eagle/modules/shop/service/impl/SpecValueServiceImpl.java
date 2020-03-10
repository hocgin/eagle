package in.hocg.eagle.modules.shop.service.impl;

import in.hocg.eagle.modules.shop.entity.SpecValue;
import in.hocg.eagle.modules.shop.mapper.SpecValueMapper;
import in.hocg.eagle.modules.shop.service.SpecValueService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * [Shop模块] 商品规格值表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-10
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SpecValueServiceImpl extends AbstractServiceImpl<SpecValueMapper, SpecValue> implements SpecValueService {

}
