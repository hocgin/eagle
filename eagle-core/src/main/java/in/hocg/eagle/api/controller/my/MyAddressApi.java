package in.hocg.eagle.api.controller.my;


import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.api.pojo.qo.MyAddressPagingApiQo;
import in.hocg.eagle.api.service.AppService;
import in.hocg.boot.logging.autoconfiguration.core.UseLogger;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.ums.pojo.qo.account.address.AccountAddressSaveQo;
import in.hocg.eagle.modules.ums.pojo.vo.account.address.AccountAddressComplexVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * [用户模块] 收货地址表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
@Api(tags = "eagle::地址管理")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api-mini/my/address")
public class MyAddressApi {
    private final AppService service;

    @PostMapping
    @UseLogger("新增我的收货地址 - 收货地址")
    @ApiOperation("新增我的收货地址 - 收货地址")
    public Result<Void> insertMyAddress(@Validated @RequestBody AccountAddressSaveQo qo) {
        service.insertMyAddress(qo);
        return Result.success();
    }

    @PutMapping("/{id:\\d+}")
    @UseLogger("修改我的收货地址 - 收货地址")
    @ApiOperation("修改我的收货地址 - 收货地址")
    public Result<Void> updateOne(@PathVariable Long id, @Validated @RequestBody AccountAddressSaveQo qo) {
        service.updateMyAddress(id, qo);
        return Result.success();
    }

    @GetMapping("/{id:\\d+}")
    @UseLogger("查询我的收货地址 - 收货地址")
    @ApiOperation("查询我的收货地址 - 收货地址")
    public Result<AccountAddressComplexVo> getMyAddress(@PathVariable Long id) {
        final IdRo ro = new IdRo();
        ro.setId(id);
        return Result.success(service.getMyAddress(ro));
    }

    @UseLogger("分页查询我的收货地址 - 收货地址")
    @ApiOperation("分页查询我的收货地址 - 收货地址")
    @PostMapping({"/_paging"})
    public Result<IPage<AccountAddressComplexVo>> pagingMyAddress(@Validated @RequestBody MyAddressPagingApiQo qo) {
        return Result.success(service.pagingMyAddress(qo));
    }

    @DeleteMapping
    @UseLogger("删除我的收货地址 - 收货地址")
    @ApiOperation("删除我的收货地址 - 收货地址")
    public Result<Void> deleteMyAddress(@Validated @RequestBody IdRo qo) {
        service.deleteMyAddress(qo);
        return Result.success();
    }
}

