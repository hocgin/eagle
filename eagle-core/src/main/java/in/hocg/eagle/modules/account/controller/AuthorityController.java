package in.hocg.eagle.modules.account.controller;


import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.mapstruct.qo.AuthorityPostQo;
import in.hocg.eagle.mapstruct.qo.AuthorityPutQo;
import in.hocg.eagle.mapstruct.qo.AuthoritySearchQo;
import in.hocg.eagle.mapstruct.qo.GrantRoleQo;
import in.hocg.eagle.mapstruct.vo.AuthorityTreeNodeVo;
import in.hocg.eagle.modules.account.service.AuthorityService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

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
    public Result<Void> deleteById(@PathVariable Integer id,
                                   @RequestParam(name = "force", required = false, defaultValue = "false") boolean force) {
        service.deleteById(id, force);
        return Result.success();
    }
    
    @GetMapping("/{id}")
    @ApiOperation("查询权限信息")
    public Result<Void> selectById(@PathVariable Serializable id) {
        return Result.success();
    }
    
    @PostMapping
    @ApiOperation("新增权限")
    public Result<Void> insert(@Validated @RequestBody AuthorityPostQo qo) {
        service.insertOne(qo);
        return Result.success();
    }
    
    @PutMapping("/{id}")
    @ApiOperation("更新权限")
    public Result<Void> update(@PathVariable Integer id,
                               @Validated @RequestBody AuthorityPutQo qo) {
        qo.setId(id);
        service.updateOne(qo);
        return Result.success();
    }
    
    @PostMapping("/_search")
    @ApiOperation("查询权限列表(目前仅支持树格式)")
    public Result<List<AuthorityTreeNodeVo>> search(@Validated @RequestBody AuthoritySearchQo qo) {
        return Result.success(service.search(qo));
    }
    
    @PostMapping("/{id}/grant/role")
    @ApiOperation("给角色授权权限")
    public Result<Void> grantRole(@PathVariable Integer id,
                                  @Validated @RequestBody GrantRoleQo qo) {
        qo.setId(id);
        service.grantRole(qo);
        return Result.success();
    }
}

