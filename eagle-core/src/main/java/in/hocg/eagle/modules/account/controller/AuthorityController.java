package in.hocg.eagle.modules.account.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.mapstruct.qo.AuthorityPostQo;
import in.hocg.eagle.mapstruct.qo.AuthorityPutQo;
import in.hocg.eagle.mapstruct.qo.AuthoritySearchQo;
import in.hocg.eagle.modules.account.service.AuthorityService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * <p>
 * [用户模块] 权限表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/authority")
public class AuthorityController {
    
    private final AuthorityService service;
    
    @DeleteMapping("/{id}")
    @ApiOperation("删除权限")
    public Result<Void> deleteById(@PathVariable Serializable id) {
        return Result.success();
    }
    
    @GetMapping("/{id}")
    @ApiOperation("查询权限信息")
    public Result<Void> selectById(@PathVariable Serializable id) {
        return Result.success();
    }
    
    @PostMapping("/")
    @ApiOperation("新增权限")
    public Result<Void> insert(@Validated @RequestBody AuthorityPostQo qo) {
        service.insertOne(qo);
        return Result.success();
    }
    
    @PutMapping("/{id}")
    @ApiOperation("更新权限")
    public Result<Void> update(@PathVariable Serializable id,
                               @Validated @RequestBody AuthorityPutQo qo) {
        qo.setId(id);
        service.updateOne(qo);
        return Result.success();
    }
    
    @PostMapping("/_search")
    @ApiOperation("分页查询权限")
    public Result<Page> search(@Validated @RequestBody AuthoritySearchQo qo) {
        return Result.success();
    }
}

