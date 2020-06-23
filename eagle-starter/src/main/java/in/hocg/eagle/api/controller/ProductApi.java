package in.hocg.eagle.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.api.pojo.qo.ProductPagingApiQo;
import in.hocg.eagle.api.service.AppService;
import in.hocg.web.aspect.logger.UseLogger;
import in.hocg.web.constant.AuthorizeConstant;
import in.hocg.web.pojo.qo.IdQo;
import in.hocg.web.result.Result;
import in.hocg.eagle.modules.pms.api.vo.ProductComplexVo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hocgin on 2020/3/17.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@RestController
@PreAuthorize(AuthorizeConstant.IS_MINI_EAGLE)
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api-mini/product")
public class ProductApi {
    private final AppService appService;

    @UseLogger("搜索商品")
    @PostMapping("/_paging")
    public Result<IPage<ProductComplexVo>> paging(@Validated @RequestBody ProductPagingApiQo qo) {
        return Result.success(appService.pagingProduct(qo));
    }

    @UseLogger("商品详情")
    @GetMapping("/{id:\\d+}")
    public Result<ProductComplexVo> selectOne(@PathVariable Long id) {
        final IdQo qo = new IdQo();
        qo.setId(id);
        return Result.success(appService.getProductById(qo));
    }

}
