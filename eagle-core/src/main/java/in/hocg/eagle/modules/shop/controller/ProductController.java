package in.hocg.eagle.modules.shop.controller;


import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.pojo.qo.Insert;
import in.hocg.eagle.basic.pojo.qo.Update;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.shop.pojo.qo.ProductPagingQo;
import in.hocg.eagle.modules.shop.pojo.qo.ProductSaveQo;
import in.hocg.eagle.modules.shop.pojo.vo.product.ProductComplexVo;
import in.hocg.eagle.modules.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * [Shop模块] 商品表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-03-10
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService service;

    @UseLogger("新增商品")
    @PostMapping
    public Result<Void> insertOne(@Validated({Insert.class}) @RequestBody ProductSaveQo qo) {
        qo.setId(null);
        service.saveOne(qo);
        return Result.success();
    }

    @UseLogger("更新商品信息")
    @PutMapping("/{id}")
    public Result<Void> updateOne(@PathVariable("id") Long id,
                                  @Validated({Update.class}) @RequestBody ProductSaveQo qo) {
        qo.setId(id);
        service.saveOne(qo);
        return Result.success();
    }

    @UseLogger("查看商品信息")
    @GetMapping("/{id}")
    public Result<ProductComplexVo> selectOne(@PathVariable("id") Long id) {
        return Result.success(service.selectOne(id));
    }

    @UseLogger("分页查询商品")
    @PostMapping("/_paging")
    public Result paging(@Validated @RequestBody ProductPagingQo qo) {
        return Result.success(service.paging(qo));
    }

}

