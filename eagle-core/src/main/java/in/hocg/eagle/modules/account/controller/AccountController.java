package in.hocg.eagle.modules.account.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.constant.AuthorizeConstant;
import in.hocg.eagle.basic.constant.GlobalConstant;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.basic.security.SecurityContext;
import in.hocg.eagle.mapstruct.qo.account.AccountSearchQo;
import in.hocg.eagle.mapstruct.qo.account.AccountUpdateStatusPutQo;
import in.hocg.eagle.mapstruct.qo.account.GrantRoleQo;
import in.hocg.eagle.mapstruct.vo.account.AccountSearchVo;
import in.hocg.eagle.mapstruct.vo.account.IdAccountComplexVo;
import in.hocg.eagle.mapstruct.vo.authority.AuthorityTreeNodeVo;
import in.hocg.eagle.modules.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    @UseLogger("当前账号信息")
    @PreAuthorize(AuthorizeConstant.IS_AUTHENTICATED)
    public Result<IdAccountComplexVo> current() {
        final Long accountId = SecurityContext.getCurrentUserId();
        return Result.success(service.selectOneComplexAndRole(accountId));
    }

    @GetMapping("/authority")
    @UseLogger("获取权限树(当前用户)")
    public Result<List<AuthorityTreeNodeVo>> selectAuthorityTreeByCurrentAccount() {
        final Long accountId = SecurityContext.getCurrentUserId();
        return Result.success(service.selectAuthorityTreeByCurrentAccount(accountId, GlobalConstant.CURRENT_PLATFORM.getCode()));
    }

    @GetMapping("/{id}")
    @UseLogger("获取账号信息")
    public Result<IdAccountComplexVo> selectOneById(@PathVariable Long id) {
        return Result.success(service.selectOneComplexAndRole(id));
    }

    @PostMapping("/{id}/grant/role")
    @UseLogger("给账号授权角色")
    public Result<Void> grantRole(@PathVariable Long id,
                                  @Validated @RequestBody GrantRoleQo qo) {
        qo.setId(id);
        service.grantRole(qo);
        return Result.success();
    }

    @PutMapping("/{id}")
    @UseLogger("更改账户状态")
    public Result<Void> updateAccountStatus(@PathVariable Long id,
                                            @Validated @RequestBody AccountUpdateStatusPutQo qo) {
        qo.setId(id);
        service.updateStatus(qo);
        return Result.success();
    }

    @PostMapping("/_search")
    @UseLogger("查询账号列表")
    public Result<IPage<AccountSearchVo>> search(@Validated @RequestBody AccountSearchQo qo) {
        return Result.success(service.search(qo));
    }

}

