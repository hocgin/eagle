package in.hocg.eagle.modules.base.controller;


import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.pojo.qo.IdsQo;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.mapstruct.qo.datadict.DataDictItemPutQo;
import in.hocg.eagle.mapstruct.qo.datadict.DataDictItemsPostQo;
import in.hocg.eagle.modules.base.entity.DataDictItem;
import in.hocg.eagle.modules.base.service.DataDictItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * [基础模块] 数据字典表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-02-14
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/data-dict/item")
public class DataDictItemController {
    private final DataDictItemService service;
    
    @UseLogger("删除数据字典项")
    @DeleteMapping
    public Result<Void> deleteItem(@RequestBody IdsQo qo) {
        service.deletes(qo);
        return Result.success();
    }
    
    @UseLogger("新增数据字典项")
    @PostMapping
    public Result<Void> insert(@RequestBody DataDictItemsPostQo qo) {
        service.insertList(qo);
        return Result.success();
    }
    
    @UseLogger("查询数据字典详情")
    @GetMapping("/{id:\\d+}")
    public Result<DataDictItem> selectOne(@PathVariable("id") Long id) {
        return Result.success(service.getById(id));
    }
    
    @UseLogger("更新数据字典项")
    @PutMapping("/{id}")
    public Result<Void> updateOne(@PathVariable Long id,
                                  @Validated @RequestBody DataDictItemPutQo qo) {
        qo.setId(id);
        service.updateOne(qo);
        return Result.success();
    }
}

