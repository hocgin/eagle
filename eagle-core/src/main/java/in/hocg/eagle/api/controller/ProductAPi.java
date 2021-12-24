package in.hocg.eagle.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.api.pojo.qo.ShoppingProductPagingApiQo;
import in.hocg.eagle.api.service.AppService;
import in.hocg.boot.logging.autoconfiguration.core.UseLogger;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.pms.api.vo.ProductComplexVo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hocgin on 2020/3/17.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api-mini/product")
public class ProductAPi {
    private final AppService appService;

    @UseLogger("搜索商品")
    @PostMapping("/_paging")
    public Result<IPage<ProductComplexVo>> pagingByShopping(@Validated @RequestBody ShoppingProductPagingApiQo qo) {
        return Result.success(appService.pagingByShopping(qo));
    }

    @UseLogger("商品详情")
    @GetMapping("/{id:\\d+}")
    public Result<ProductComplexVo> getByShoppingAndId(@PathVariable Long id) {
        return Result.success(appService.getByShoppingAndId(id));
    }

}
