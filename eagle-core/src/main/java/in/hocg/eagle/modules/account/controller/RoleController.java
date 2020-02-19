package in.hocg.eagle.modules.account.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.mapstruct.qo.GrantAuthorityQo;
import in.hocg.eagle.mapstruct.qo.RolePostQo;
import in.hocg.eagle.mapstruct.qo.RolePutQo;
import in.hocg.eagle.mapstruct.qo.RoleSearchQo;
import in.hocg.eagle.mapstruct.vo.RoleComplexVo;
import in.hocg.eagle.mapstruct.vo.RoleSearchVo;
import in.hocg.eagle.modules.account.service.RoleService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * [用户模块] 角色表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/role")
public class RoleController {
    
    private final RoleService service;
    
    @DeleteMapping("/{id}")
    @ApiOperation("删除角色")
    public Result<Void> deleteById(@PathVariable Long id) {
        service.deleteOne(id);
        return Result.success();
    }
    
    @GetMapping("/{id}")
    @ApiOperation("角色详情")
    public Result<RoleComplexVo> selectById(@PathVariable Long id) {
        return Result.success(service.selectOne(id));
    }
    
    @PostMapping
    @ApiOperation("新增角色")
    public Result<Void> insert(@Validated @RequestBody RolePostQo qo) {
        service.insertOne(qo);
        return Result.success();
    }
    
    @PutMapping("/{id}")
    @ApiOperation("修改角色")
    public Result<Void> update(@PathVariable Long id,
                               @Validated @RequestBody RolePutQo qo) {
        qo.setId(id);
        service.updateOne(qo);
        return Result.success();
    }
    
    @PostMapping("/_search")
    @ApiOperation("分页查询角色列表")
    public Result<IPage<RoleSearchVo>> search(@Validated @RequestBody RoleSearchQo qo) {
        return Result.success(service.search(qo));
    }
    
    @PostMapping("/{id}/grant/authority")
    @ApiOperation("给角色授权权限")
    public Result<Void> grantAuthority(@PathVariable Long id,
                                       @Validated @RequestBody GrantAuthorityQo qo) {
        qo.setId(id);
        service.grantAuthority(qo);
        return Result.success();
    }
}

