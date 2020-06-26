package in.hocg.eagle.modules.ums.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.ext.mybatis.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.GroupMemberSource;
import in.hocg.eagle.basic.constant.CodeEnum;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.modules.ums.mapstruct.AccountGroupMemberMapping;
import in.hocg.eagle.modules.ums.entity.Account;
import in.hocg.eagle.modules.ums.entity.AccountGroup;
import in.hocg.eagle.modules.ums.entity.AccountGroupMember;
import in.hocg.eagle.modules.ums.mapper.AccountGroupMemberMapper;
import in.hocg.eagle.modules.ums.pojo.qo.account.group.AccountGroupMemberDeleteQo;
import in.hocg.eagle.modules.ums.pojo.qo.account.group.AccountGroupMemberPageQo;
import in.hocg.eagle.modules.ums.pojo.qo.account.group.JoinMemberQo;
import in.hocg.eagle.modules.ums.pojo.vo.account.group.AccountGroupMemberComplexVo;
import in.hocg.eagle.modules.ums.service.AccountGroupMemberService;
import in.hocg.eagle.modules.ums.service.AccountGroupService;
import in.hocg.eagle.modules.ums.service.AccountService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import in.hocg.eagle.utils.web.ResultUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * [用户模块] 账号分组成员表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AccountGroupMemberServiceImpl extends AbstractServiceImpl<AccountGroupMemberMapper, AccountGroupMember> implements AccountGroupMemberService {
    private final AccountGroupMemberMapping mapping;
    private final AccountGroupService accountGroupService;
    private final AccountService accountService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllByGroupId(Long groupId) {
        baseMapper.deleteAllByGroupId(groupId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<AccountGroupMemberComplexVo> pagingWithComplex(AccountGroupMemberPageQo qo) {
        final Long groupId = qo.getGroupId();
        final AccountGroup group = accountGroupService.getById(groupId);
        if (Objects.isNull(group)) {
            return ResultUtils.emptyPage(qo);
        }
        Optional<GroupMemberSource> memberSourceOpt = CodeEnum.of(group.getMemberSource(), GroupMemberSource.class);
        if (!memberSourceOpt.isPresent()) {
            return ResultUtils.emptyPage(qo);
        }
        final GroupMemberSource memberSource = memberSourceOpt.get();
        IPage<Account> result = ResultUtils.emptyPage(qo);
        if (memberSource.equals(GroupMemberSource.All)) {
            result = accountService.page(qo.page());
        } else if (memberSource.equals(GroupMemberSource.Custom)) {
            result = baseMapper.pagingWithComplex(qo, qo.page());
        }

        return result.convert(this::convertComplex);
    }

    private AccountGroupMemberComplexVo convertComplex(Account entity) {
        return mapping.asAccountGroupMemberComplexVo(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteListWithMember(AccountGroupMemberDeleteQo qo) {
        final Long groupId = qo.getGroupId();
        final AccountGroup group = accountGroupService.getById(groupId);
        if (!LangUtils.equals(GroupMemberSource.Custom.getCode(), group.getMemberSource())) {
            throw ServiceException.wrap("群组不支持移出成员");
        }

        for (Long memberId : qo.getMembers()) {
            final Optional<AccountGroupMember> accountGroupMemberOpt = selectOneByGroupIdAndAccountId(groupId, memberId);
            accountGroupMemberOpt.ifPresent(accountGroupMember -> removeById(accountGroupMember.getId()));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void join(JoinMemberQo qo) {
        final Long userId = qo.getUserId();
        final LocalDateTime createdAt = qo.getCreatedAt();
        final Long groupId = qo.getGroupId();
        final AccountGroup accountGroup = accountGroupService.getById(groupId);
        ValidUtils.notNull(accountGroup, "群组不存在");
        ValidUtils.isTrue(LangUtils.equals(accountGroup.getMemberSource(), GroupMemberSource.Custom.getCode()), "群组不支持添加成员");

        final List<Long> members = qo.getMembers();
        if (!CollectionUtils.isEmpty(members)) {
            for (Long memberId : members) {
                final Optional<AccountGroupMember> accountGroupMemberOpt = selectOneByGroupIdAndAccountId(groupId, memberId);
                if (accountGroupMemberOpt.isPresent()) {
                    continue;
                }
                this.validInsert(new AccountGroupMember()
                    .setGroupId(groupId)
                    .setCreator(userId)
                    .setCreatedAt(createdAt)
                    .setAccountId(memberId));
            }
        }
    }

    private Optional<AccountGroupMember> selectOneByGroupIdAndAccountId(Long groupId, Long accountId) {
        return lambdaQuery().eq(AccountGroupMember::getGroupId, groupId)
            .eq(AccountGroupMember::getAccountId, accountId).oneOpt();
    }

}
