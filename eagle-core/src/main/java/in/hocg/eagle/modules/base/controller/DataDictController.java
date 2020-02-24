package in.hocg.eagle.modules.base.controller;


import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.mapstruct.qo.ExamplePostQo;
import in.hocg.eagle.mapstruct.qo.ExamplePutQo;
import in.hocg.eagle.mapstruct.qo.ExampleSearchQo;
import in.hocg.eagle.modules.base.service.DataDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

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
    
    
    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable Serializable id) {
        return Result.success();
    }
    
    @GetMapping("/{id}")
    public Result<Void> selectById(@PathVariable Serializable id) {
        return Result.success();
    }
    
    @PostMapping
    public Result<Void> insert(@Validated @RequestBody ExamplePostQo qo) {
        return Result.success();
    }
    
    @PutMapping("/{id}")
    public Result<Void> updateOne(@PathVariable Serializable id,
                                  @Validated @RequestBody ExamplePutQo qo) {
        return Result.success();
    }
    
    @PostMapping("/_search")
    public Result<Void> search(@Validated @RequestBody ExampleSearchQo qo) {
        return Result.success();
    }
}

