package in.hocg.eagle.modules.pms.controller;


import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.constant.AuthorizeConstant;
import in.hocg.eagle.basic.pojo.qo.Insert;
import in.hocg.eagle.basic.pojo.qo.Update;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.pms.pojo.qo.ProductPagingQo;
import in.hocg.eagle.modules.pms.pojo.qo.ProductSaveQo;
import in.hocg.eagle.modules.pms.pojo.vo.product.ProductComplexVo;
import in.hocg.eagle.modules.pms.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize(AuthorizeConstant.IS_MANAGER)
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService service;

    @UseLogger("新增 - 商品")
    @PostMapping
    public Result<Void> insertOne(@Validated({Insert.class}) @RequestBody ProductSaveQo qo) {
        qo.setId(null);
        service.saveOne(qo);
        return Result.success();
    }

    @UseLogger("更新 - 商品")
    @PutMapping("/{id}")
    public Result<Void> updateOne(@PathVariable("id") Long id,
                                  @Validated({Update.class}) @RequestBody ProductSaveQo qo) {
        qo.setId(id);
        service.saveOne(qo);
        return Result.success();
    }

    @UseLogger("删除 - 商品")
    @DeleteMapping("/{id}")
    public Result<Void> deleteOne(@PathVariable("id") Long id) {
        final ProductSaveQo qo = new ProductSaveQo();
        qo.setId(id);
        qo.setDeleteStatus(1);
        service.saveOne(qo);
        return Result.success();
    }

    @UseLogger("查看信息 - 商品")
    @GetMapping("/{id}")
    public Result<ProductComplexVo> selectOne(@PathVariable("id") Long id) {
        return Result.success(service.selectOne(id));
    }

    @UseLogger("分页查询 - 商品")
    @PostMapping("/_paging")
    public Result paging(@Validated @RequestBody ProductPagingQo qo) {
        return Result.success(service.paging(qo));
    }

    @UseLogger("查询所有 - 商品")
    @PostMapping("/all")
    public Result selectAll() {
        final ProductPagingQo qo = new ProductPagingQo();
        return Result.success(service.paging(qo).getRecords());
    }

}

