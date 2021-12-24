package in.hocg.eagle.modules.ums.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.boot.logging.autoconfiguration.core.UseLogger;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.basic.pojo.ro.Insert;
import in.hocg.eagle.basic.pojo.ro.Update;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.ums.pojo.qo.account.group.*;
import in.hocg.eagle.modules.ums.pojo.vo.account.group.AccountGroupComplexVo;
import in.hocg.eagle.modules.ums.service.AccountGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * [用户模块] 账号分组表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/account-group")
public class AccountGroupController {
    private final AccountGroupService service;

    @PostMapping
    @UseLogger("新增 - 账号组")
    public Result insertOne(@Validated({Insert.class}) @RequestBody AccountGroupSaveQo qo) {
        qo.setId(null);
        service.saveOne(qo);
        return Result.success();
    }

    @PutMapping("/{id:\\d+}")
    @UseLogger("修改 - 账号组")
    public Result updateOne(@PathVariable Long id,
                            @Validated({Update.class}) @RequestBody AccountGroupSaveQo qo) {
        qo.setId(id);
        service.saveOne(qo);
        return Result.success();
    }

    @GetMapping({"/{id:\\d+}:complex"})
    @UseLogger("分页查询 - 账号组")
    public Result<AccountGroupComplexVo> selectOne(@PathVariable Long id) {
        return Result.success(service.selectOne(id));
    }

    @PostMapping({"/_paging"})
    @UseLogger("分页查询 - 账号组")
    public Result<IPage<AccountGroupComplexVo>> paging(@Validated @RequestBody AccountGroupPageQo qo) {
        return Result.success(service.pagingWithComplex(qo));
    }

    @DeleteMapping("/{id:\\d+}")
    @UseLogger("删除 - 账号组")
    public Result deleteOne(@PathVariable Long id) {
        final IdRo qo = new IdRo();
        qo.setId(id);
        service.deleteOne(qo);
        return Result.success();
    }

    @PostMapping("/{groupId:\\d+}/join")
    @UseLogger("添加成员 - 账号组")
    public Result join(@PathVariable Long groupId,
                       @Validated @RequestBody JoinMemberQo qo) {
        qo.setGroupId(groupId);
        service.join(qo);
        return Result.success();
    }

    @PostMapping("/{groupId:\\d+}/member/_paging")
    @UseLogger("分页查询成员 - 账号组")
    public Result pagingWithMember(@PathVariable Long groupId,
                                   @Validated @RequestBody AccountGroupMemberPageQo qo) {
        qo.setGroupId(groupId);
        return Result.success(service.pagingWithMember(qo));
    }

    @DeleteMapping("/{groupId:\\d+}/member")
    @UseLogger("删除成员 - 账号组")
    public Result deleteOneWithMember(@PathVariable Long groupId,
                                      @Validated @RequestBody AccountGroupMemberDeleteQo qo) {
        qo.setGroupId(groupId);
        service.deleteListWithMember(qo);
        return Result.success();
    }
}

