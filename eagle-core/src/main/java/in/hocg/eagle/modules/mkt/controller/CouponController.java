package in.hocg.eagle.modules.mkt.controller;


import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.pojo.ro.Insert;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.mkt.pojo.qo.CouponPagingQo;
import in.hocg.eagle.modules.mkt.pojo.qo.CouponSaveQo;
import in.hocg.eagle.modules.mkt.pojo.qo.GiveCouponQo;
import in.hocg.eagle.modules.mkt.service.CouponService;
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
@RequestMapping("/api/coupon")
public class CouponController {
    private final CouponService service;

    @UseLogger("分页查询 - 优惠券模版")
    @PostMapping("/_paging")
    public Result paging(@Validated @RequestBody CouponPagingQo qo) {
        return Result.success(service.paging(qo));
    }

    @UseLogger("创建 - 优惠券模版")
    @PostMapping
    public Result insertOne(@Validated({Insert.class}) @RequestBody CouponSaveQo qo) {
        service.saveOne(qo);
        return Result.success();
    }

    @UseLogger("查看详情 - 优惠券")
    @GetMapping("/{id:\\d+}:complex")
    public Result selectOne(@PathVariable Long id) {
        return Result.success(service.selectOne(id));
    }

    @UseLogger("赠送优惠券 - 优惠券")
    @PostMapping("/{id:\\d+}/give")
    public Result give(@PathVariable Long id,
                       @Validated @RequestBody GiveCouponQo qo) {
        qo.setId(id);
        service.giveToUsers(qo);
        return Result.success();
    }

    @UseLogger("撤回所有未使用优惠券 - 优惠券")
    @PostMapping("/{id:\\d+}/revoke")
    public Result revoke(@PathVariable Long id) {
        service.revoke(id);
        return Result.success();
    }
}

