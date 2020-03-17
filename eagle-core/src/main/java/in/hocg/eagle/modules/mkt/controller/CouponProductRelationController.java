package in.hocg.eagle.modules.mkt.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 优惠券可用商品表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-03-17
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/mkt/coupon-product-relation")
public class CouponProductRelationController {

}

