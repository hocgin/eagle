package in.hocg.eagle.modules.mkt.service.impl;

import in.hocg.eagle.modules.mkt.entity.CouponProductRelation;
import in.hocg.eagle.modules.mkt.mapper.CouponProductRelationMapper;
import in.hocg.eagle.modules.mkt.service.CouponProductRelationService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 优惠券可用商品表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-17
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CouponProductRelationServiceImpl extends AbstractServiceImpl<CouponProductRelationMapper, CouponProductRelation> implements CouponProductRelationService {

}
