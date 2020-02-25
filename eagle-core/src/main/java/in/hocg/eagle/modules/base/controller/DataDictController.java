package in.hocg.eagle.modules.base.controller;


import in.hocg.eagle.basic.pojo.KeyValue;
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
import java.util.List;

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
    
    @GetMapping("/{code:\\w+}")
    public Result<List<KeyValue>> selectListDictItem(@PathVariable("code") String code) {
        return Result.success(service.selectListDictItemByCode(code));
    }
    
    @DeleteMapping("/{id:\\d+}")
    public Result<Void> deleteById(@PathVariable Integer id) {
        return Result.success();
    }
    
    @GetMapping("/{id:\\d+}")
    public Result<Void> selectById(@PathVariable Integer id) {
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

