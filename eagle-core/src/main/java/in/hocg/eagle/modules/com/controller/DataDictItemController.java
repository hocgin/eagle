package in.hocg.eagle.modules.com.controller;


import in.hocg.web.aspect.logger.UseLogger;
import in.hocg.web.pojo.qo.IdsQo;
import in.hocg.web.result.Result;
import in.hocg.eagle.modules.com.pojo.qo.datadict.item.DataDictItemUpdateQo;
import in.hocg.eagle.modules.com.pojo.qo.datadict.item.DataDictItemsBatchInsertQo;
import in.hocg.eagle.modules.com.pojo.vo.datadict.item.DataDictItemComplexVo;
import in.hocg.eagle.modules.com.service.DataDictItemService;
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
    public Result<Void> deletes(@RequestBody IdsQo qo) {
        service.batchDelete(qo);
        return Result.success();
    }

    @UseLogger("新增数据字典项")
    @PostMapping
    public Result<Void> insert(@RequestBody DataDictItemsBatchInsertQo qo) {
        service.batchInsert(qo);
        return Result.success();
    }

    @UseLogger("查询数据字典详情")
    @GetMapping("/{id:\\d+}")
    public Result<DataDictItemComplexVo> selectOne(@PathVariable("id") Long id) {
        return Result.success(service.selectOne(id));
    }

    @UseLogger("更新数据字典项")
    @PutMapping("/{id}")
    public Result<Void> updateOne(@PathVariable Long id,
                                  @Validated @RequestBody DataDictItemUpdateQo qo) {
        qo.setId(id);
        service.updateOne(qo);
        return Result.success();
    }
}

