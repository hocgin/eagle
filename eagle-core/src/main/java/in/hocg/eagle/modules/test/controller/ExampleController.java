package in.hocg.eagle.modules.test.controller;


import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.mapstruct.qo.ExamplePostQo;
import in.hocg.eagle.mapstruct.qo.ExamplePutQo;
import in.hocg.eagle.mapstruct.qo.ExampleSearchQo;
import in.hocg.eagle.modules.test.service.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/test/example")
public class ExampleController {
    private final ExampleService service;
    
    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable Long id) {
        return Result.success();
    }
    
    @GetMapping("/{id}")
    public Result<Void> selectById(@PathVariable Long id) {
        return Result.success();
    }
    
    @PostMapping("/")
    public Result<Void> insert(@Validated @RequestBody ExamplePostQo qo) {
        return Result.success();
    }
    
    @PutMapping("/{id}")
    public Result<Void> insert(@PathVariable Long id,
                               @Validated @RequestBody ExamplePutQo qo) {
        return Result.success();
    }
    
    @PostMapping("/_search")
    public Result<Void> search(@Validated @RequestBody ExampleSearchQo qo) {
        return Result.success();
    }
    
}

