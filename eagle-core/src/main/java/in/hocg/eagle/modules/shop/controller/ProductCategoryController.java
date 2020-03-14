package in.hocg.eagle.modules.shop.controller;


import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.pojo.qo.Insert;
import in.hocg.eagle.basic.pojo.qo.Update;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.shop.pojo.qo.category.ProductCategorySaveQo;
import in.hocg.eagle.modules.shop.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * [Shop模块] 商品品类表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-03-14
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/shop/product-category")
public class ProductCategoryController {
    private final ProductCategoryService service;

    @UseLogger("新增商品品类")
    @PostMapping
    public Result insertOne(@Validated({Insert.class}) @RequestBody ProductCategorySaveQo qo) {
        qo.setId(null);
        service.saveOne(qo);
        return Result.success();
    }

    @UseLogger("更新商品品类")
    @PutMapping("/{id}")
    public Result updateQo(@PathVariable("id") Long id,
                           @Validated({Update.class}) @RequestBody ProductCategorySaveQo qo) {
        qo.setId(id);
        service.saveOne(qo);
        return Result.success();
    }

}

