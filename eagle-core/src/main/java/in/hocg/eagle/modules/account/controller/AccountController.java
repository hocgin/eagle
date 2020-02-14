package in.hocg.eagle.modules.account.controller;


import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.mapstruct.vo.IdAccountComplexVo;
import in.hocg.eagle.modules.account.service.AccountService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * <p>
 * [用户模块] 账号表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService service;
    
    @GetMapping("/{id}")
    @ApiOperation("给账号信息")
    public Result<IdAccountComplexVo> id(@PathVariable Serializable id) {
        return Result.success(service.selectOneComplex(id));
    }
    
    @PostMapping("/{id}/grant/role")
    @ApiOperation("给账号授权角色")
    public Result<Void> grantRole(@PathVariable Serializable id) {
        return Result.success();
    }
    
    @PostMapping("/{id}/grant/authority")
    @ApiOperation("给账号授权权限")
    public Result<Void> grantAuthority(@PathVariable Serializable id) {
        return Result.success();
    }
}

