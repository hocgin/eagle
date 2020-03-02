package in.hocg.eagle.modules.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.mapstruct.qo.datadict.DataDictDeleteQo;
import in.hocg.eagle.mapstruct.qo.datadict.DataDictPostQo;
import in.hocg.eagle.mapstruct.qo.datadict.DataDictPutQo;
import in.hocg.eagle.mapstruct.qo.datadict.DataDictSearchQo;
import in.hocg.eagle.mapstruct.vo.DataDictComplexVo;
import in.hocg.eagle.mapstruct.vo.DataDictSearchVo;
import in.hocg.eagle.modules.base.service.DataDictService;
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
@RequestMapping("/api/data-dict")
public class DataDictController {
    private final DataDictService service;
    
    @UseLogger("查询数据字典详情")
    @GetMapping("/{id:\\d+}:complex")
    public Result<DataDictComplexVo> selectOneDataDictComplexById(@PathVariable("id") Long id) {
        return Result.success(service.selectOne(id));
    }
    
    @UseLogger("删除数据字典")
    @DeleteMapping
    public Result<Void> deletes(@RequestBody DataDictDeleteQo qo) {
        service.deletes(qo);
        return Result.success();
    }
    
    @UseLogger("新增数据字典")
    @PostMapping
    public Result<Void> insert(@Validated @RequestBody DataDictPostQo qo) {
        service.insertOne(qo);
        return Result.success();
    }
    
    @UseLogger("更新数据字典")
    @PutMapping("/{id}")
    public Result<Void> updateOne(@PathVariable Long id,
                                  @Validated @RequestBody DataDictPutQo qo) {
        qo.setId(id);
        service.updateOne(qo);
        return Result.success();
    }
    
    @UseLogger("搜索数据字典列表")
    @PostMapping("/_search")
    public Result<IPage<DataDictSearchVo>> search(@Validated @RequestBody DataDictSearchQo qo) {
        return Result.success(service.search(qo));
    }
    
}

