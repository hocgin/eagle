package in.hocg.eagle.modules.account.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.mapstruct.qo.authority.GrantAuthorityQo;
import in.hocg.eagle.mapstruct.qo.role.RolePostQo;
import in.hocg.eagle.mapstruct.qo.role.RolePutQo;
import in.hocg.eagle.mapstruct.qo.role.RoleSearchQo;
import in.hocg.eagle.mapstruct.vo.role.RoleComplexVo;
import in.hocg.eagle.mapstruct.vo.role.RoleSearchVo;
import in.hocg.eagle.modules.account.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @UseLogger("删除角色")
    public Result<Void> deleteById(@PathVariable Long id) {
        service.deleteOne(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @UseLogger("角色详情")
    public Result<RoleComplexVo> selectById(@PathVariable Long id) {
        return Result.success(service.selectOne(id));
    }

    @PostMapping
    @UseLogger("新增角色")
    public Result<Void> insert(@Validated @RequestBody RolePostQo qo) {
        service.insertOne(qo);
        return Result.success();
    }

    @PutMapping("/{id}")
    @UseLogger("修改角色")
    public Result<Void> update(@PathVariable Long id,
                               @Validated @RequestBody RolePutQo qo) {
        qo.setId(id);
        service.updateOne(qo);
        return Result.success();
    }

    @PostMapping("/_search")
    @UseLogger("分页查询角色列表")
    public Result<IPage<RoleSearchVo>> search(@Validated @RequestBody RoleSearchQo qo) {
        return Result.success(service.search(qo));
    }


    @PostMapping("/all")
    @UseLogger("查询所有角色列表")
    public Result<List<RoleSearchVo>> all() {
        final RoleSearchQo qo = new RoleSearchQo();
        qo.setSize(Integer.MAX_VALUE);
        return Result.success(service.search(qo).getRecords());
    }

    @PostMapping("/{id}/grant/authority")
    @UseLogger("给角色授权权限")
    public Result<Void> grantAuthority(@PathVariable Long id,
                                       @Validated @RequestBody GrantAuthorityQo qo) {
        qo.setId(id);
        service.grantAuthority(qo);
        return Result.success();
    }
}

