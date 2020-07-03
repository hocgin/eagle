package in.hocg.eagle.api.controller;

import in.hocg.eagle.api.pojo.qo.SelfCouponPagingApiQo;
import in.hocg.eagle.api.service.AppService;
import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hocgin on 2020/3/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@RestController
//@PreAuthorize(AuthorizeConstant.IS_MINI_EAGLE)
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api-mini/coupon")
public class CouponApi {
    private final AppService service;

    @UseLogger("搜索个人优惠券")
    @PostMapping("/_paging")
    public Result paging(@Validated @RequestBody SelfCouponPagingApiQo qo) {
        return Result.success(service.pagingSelfCoupon(qo));
    }
}
