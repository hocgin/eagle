package in.hocg.eagle.api.controller.my;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.api.pojo.qo.MyCouponPagingApiQo;
import in.hocg.eagle.api.service.AppService;
import in.hocg.boot.logging.autoconfiguration.core.UseLogger;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.mkt.pojo.vo.CouponAccountComplexVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "eagle::优惠券")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api-mini/my/coupon")
public class MyCouponAPi {
    private final AppService service;

    @UseLogger("搜索个人优惠券 - 个人优惠券")
    @ApiOperation("搜索个人优惠券 - 个人优惠券")
    @PostMapping("/_paging")
    public Result<IPage<CouponAccountComplexVo>> paging(@Validated @RequestBody MyCouponPagingApiQo qo) {
        return Result.success(service.pagingMyCoupon(qo));
    }


    @UseLogger("领取优惠券 - 个人优惠券")
    @ApiOperation("领取优惠券 - 个人优惠券")
    @PostMapping("/get")
    public Result getCoupon() {
        return Result.success();
    }
}
