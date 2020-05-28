package in.hocg.eagle.modules.ums.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.modules.ums.mapstruct.AccountGroupMapping;
import in.hocg.eagle.modules.ums.entity.AccountGroup;
import in.hocg.eagle.modules.ums.mapper.AccountGroupMapper;
import in.hocg.eagle.modules.ums.pojo.qo.account.group.*;
import in.hocg.eagle.modules.ums.pojo.vo.account.group.AccountGroupComplexVo;
import in.hocg.eagle.modules.ums.pojo.vo.account.group.AccountGroupMemberComplexVo;
import in.hocg.eagle.modules.ums.service.AccountGroupMemberService;
import in.hocg.eagle.modules.ums.service.AccountGroupService;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * [用户模块] 账号分组表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AccountGroupServiceImpl extends AbstractServiceImpl<AccountGroupMapper, AccountGroup> implements AccountGroupService {
    private final AccountGroupMapping mapping;
    private final AccountGroupMemberService accountGroupMemberService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(AccountGroupSaveQo qo) {
        final LocalDateTime createdAt = qo.getCreatedAt();
        final Long userId = qo.getUserId();
        final Long id = qo.getId();
        AccountGroup entity = mapping.asAccountGroup(qo);
        if (Objects.isNull(id)) {
            entity.setCreatedAt(createdAt);
            entity.setCreator(userId);
        } else {
            entity.setLastUpdatedAt(createdAt);
            entity.setLastUpdater(userId);
        }
        validInsertOrUpdate(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOne(IdQo qo) {
        final Long groupId = qo.getId();
        removeById(groupId);
        accountGroupMemberService.deleteAllByGroupId(groupId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountGroupComplexVo selectOne(Long id) {
        final AccountGroup entity = getById(id);
        ValidUtils.notNull(entity, "数据不存在");
        return convertComplex(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<AccountGroupComplexVo> pagingWithComplex(AccountGroupPageQo qo) {
        return baseMapper.pagingWithComplex(qo, qo.page())
            .convert(this::convertComplex);
    }

    public AccountGroupComplexVo convertComplex(AccountGroup entity) {
        return mapping.asAccountGroupComplexVo(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void join(JoinMemberQo qo) {
        accountGroupMemberService.join(qo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<AccountGroupMemberComplexVo> pagingWithMember(AccountGroupMemberPageQo qo) {
        return accountGroupMemberService.pagingWithComplex(qo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteListWithMember(AccountGroupMemberDeleteQo qo) {
        accountGroupMemberService.deleteListWithMember(qo);
    }

}
