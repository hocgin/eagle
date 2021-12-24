package in.hocg.eagle.modules.ums.controller;


import in.hocg.boot.logging.autoconfiguration.core.UseLogger;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.ums.pojo.qo.authority.AuthorityInsertQo;
import in.hocg.eagle.modules.ums.pojo.qo.authority.AuthoritySearchQo;
import in.hocg.eagle.modules.ums.pojo.qo.authority.AuthorityUpdateQo;
import in.hocg.eagle.modules.ums.pojo.qo.role.GrantRoleQo;
import in.hocg.eagle.modules.ums.pojo.vo.authority.AuthorityComplexAndRoleVo;
import in.hocg.eagle.modules.ums.pojo.vo.authority.AuthorityTreeNodeVo;
import in.hocg.eagle.modules.ums.entity.Authority;
import in.hocg.eagle.modules.ums.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @UseLogger("删除权限")
    public Result<Void> deleteOne(@PathVariable Long id,
                                  @RequestParam(name = "force", required = false, defaultValue = "false") boolean force) {
        service.deleteById(id, force);
        return Result.success();
    }

    @GetMapping("/{id}")
    @UseLogger("查询权限信息")
    public Result<Authority> selectOne(@PathVariable Integer id) {
        return Result.success(service.getById(id));
    }


    @GetMapping("/{id:\\d+}:complex")
    @UseLogger("查询权限信息")
    public Result<AuthorityComplexAndRoleVo> selectOneAuthorityComplexAndRole(@PathVariable Long id) {
        return Result.success(service.selectOne(id));
    }

    @PostMapping
    @UseLogger("新增权限")
    public Result<Void> insert(@Validated @RequestBody AuthorityInsertQo qo) {
        service.insertOne(qo);
        return Result.success();
    }

    @PutMapping("/{id}")
    @UseLogger("更新权限")
    public Result<Void> update(@PathVariable Long id,
                               @Validated @RequestBody AuthorityUpdateQo qo) {
        qo.setId(id);
        service.updateOne(qo);
        return Result.success();
    }

    @PostMapping({"/_search", "/_paging"})
    @UseLogger("查询权限列表")
    public Result<List<Authority>> search(@Validated @RequestBody AuthoritySearchQo qo) {
        return Result.success(service.search(qo));
    }

    @PostMapping("/tree")
    @UseLogger("查询权限列表(目前仅支持树格式)")
    public Result<List<AuthorityTreeNodeVo>> tree(@Validated @RequestBody AuthoritySearchQo qo) {
        return Result.success(service.tree(qo));
    }

    @PostMapping("/{id}/grant/role")
    @UseLogger("给角色授权权限")
    public Result<Void> grantRole(@PathVariable Long id,
                                  @Validated @RequestBody GrantRoleQo qo) {
        qo.setId(id);
        service.grantRole(qo);
        return Result.success();
    }
}

