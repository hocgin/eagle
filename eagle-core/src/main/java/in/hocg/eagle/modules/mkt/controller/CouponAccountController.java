package in.hocg.eagle.modules.mkt.controller;


import in.hocg.boot.logging.autoconfiguration.core.UseLogger;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.mkt.pojo.qo.CouponAccountPagingQo;
import in.hocg.eagle.modules.mkt.service.CouponAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 优惠券表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-03-17
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/coupon-account")
public class CouponAccountController {
    private final CouponAccountService service;

    @UseLogger("分页查询 - 用户优惠券")
    @PostMapping("/_paging")
    public Result paging(@Validated @RequestBody CouponAccountPagingQo qo) {
        return Result.success(service.paging(qo));
    }

    @UseLogger("撤回指定未使用优惠券 - 用户优惠券")
    @PostMapping("/{id:\\d+}/revoke")
    public Result revoke(@PathVariable Long id) {
        service.revokeById(id);
        return Result.success();
    }

}

