package in.hocg.eagle.modules.pms.controller;


import in.hocg.web.aspect.logger.UseLogger;
import in.hocg.web.pojo.qo.Insert;
import in.hocg.web.pojo.qo.Update;
import in.hocg.web.result.Result;
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
@RequestMapping("/api/product-category")
public class ProductCategoryController {
    private final ProductCategoryService service;

    @UseLogger("新增 - 商品品类")
    @PostMapping
    public Result insertOne(@Validated({Insert.class}) @RequestBody ProductCategorySaveQo qo) {
        qo.setId(null);
        service.saveOne(qo);
        return Result.success();
    }

    @UseLogger("获取 - 商品品类树")
    @PostMapping("/tree")
    public Result tree(@Validated @RequestBody ProductCategorySearchQo qo) {
        return Result.success(service.tree(qo));
    }

    @UseLogger("更新 - 商品品类")
    @PutMapping("/{id:\\d+}")
    public Result updateOne(@PathVariable("id") Long id,
                            @Validated({Update.class}) @RequestBody ProductCategorySaveQo qo) {
        qo.setId(id);
        service.saveOne(qo);
        return Result.success();
    }

    @UseLogger("查看 - 商品品类详情")
    @GetMapping("/{id:\\d+}:complex")
    public Result selectOne(@PathVariable("id") Long id) {
        return Result.success(service.selectOne(id));
    }

    @UseLogger("删除 - 商品品类及其子品类")
    @DeleteMapping("/{id:\\d+}")
    public Result deleteAll(@PathVariable("id") Long id) {
        service.deleteAll(id);
        return Result.success();
    }

}

