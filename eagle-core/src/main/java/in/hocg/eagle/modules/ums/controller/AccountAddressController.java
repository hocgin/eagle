package in.hocg.eagle.modules.ums.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.ums.pojo.qo.account.address.AccountAddressPageQo;
import in.hocg.eagle.modules.ums.pojo.qo.account.address.AccountAddressSaveQo;
import in.hocg.eagle.modules.ums.pojo.vo.account.address.AccountAddressComplexVo;
import in.hocg.eagle.modules.ums.service.AccountAddressService;
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
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/address")
public class AccountAddressController {
    private final AccountAddressService service;

    @PostMapping
    @UseLogger("新增 - 收货地址")
    public Result insertOne(@Validated @RequestBody AccountAddressSaveQo qo) {
        qo.setId(null);
        service.saveOne(qo);
        return Result.success();
    }

    @PutMapping("/{id:\\d+}")
    @UseLogger("修改 - 收货地址")
    public Result updateOne(@PathVariable Long id,
                            @Validated @RequestBody AccountAddressSaveQo qo) {
        qo.setId(id);
        service.saveOne(qo);
        return Result.success();
    }

    @UseLogger("分页查询 - 收货地址")
    @PostMapping({"/_paging"})
    public Result<IPage<AccountAddressComplexVo>> _paging(@Validated @RequestBody AccountAddressPageQo qo) {
        return Result.success(service.paging(qo));
    }


    @DeleteMapping
    @UseLogger("删除 - 收货地址")
    public Result deleteOne(@Validated @RequestBody IdQo qo) {
        service.deleteOne(qo);
        return Result.success();
    }

}

