package in.hocg.eagle.modules.pms.controller;


import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.pojo.qo.Insert;
import in.hocg.eagle.basic.pojo.qo.Update;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.pms.pojo.qo.category.ProductCategorySaveQo;
import in.hocg.eagle.modules.pms.pojo.qo.category.ProductCategorySearchQo;
import in.hocg.eagle.modules.pms.service.ProductCategoryService;
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

    @UseLogger("获取品类树")
    @GetMapping
    public Result tree(@Validated @RequestBody ProductCategorySearchQo qo) {
        return Result.success(service.tree(qo));
    }

    @UseLogger("更新商品品类")
    @PutMapping("/{id}")
    public Result updateOne(@PathVariable("id") Long id,
                            @Validated({Update.class}) @RequestBody ProductCategorySaveQo qo) {
        qo.setId(id);
        service.saveOne(qo);
        return Result.success();
    }

    @UseLogger("删除商品品类及其子品类")
    @DeleteMapping("/{id}")
    public Result deleteAll(@PathVariable("id") Long id) {
        service.deleteCurrentAndChildren(id);
        return Result.success();
    }

}

