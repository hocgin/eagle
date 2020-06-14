package in.hocg.eagle.modules.ums.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import in.hocg.web.manager.MailManager;
import in.hocg.web.manager.OssManager;
import in.hocg.web.manager.SmsManager;
import in.hocg.web.manager.redis.RedisManager;
import in.hocg.eagle.modules.ums.entity.Account;
import in.hocg.eagle.modules.ums.entity.Authority;
import in.hocg.eagle.modules.ums.entity.Role;
import in.hocg.eagle.modules.ums.mapper.AccountMapper;
import in.hocg.eagle.modules.ums.mapstruct.AccountMapping;
import in.hocg.eagle.modules.ums.mapstruct.AuthorityMapping;
import in.hocg.eagle.modules.ums.mapstruct.RoleMapping;
import in.hocg.eagle.modules.ums.pojo.qo.account.*;
import in.hocg.eagle.modules.ums.pojo.vo.account.AccountComplexVo;
import in.hocg.eagle.modules.ums.pojo.vo.account.IdAccountComplexVo;
import in.hocg.eagle.modules.ums.pojo.vo.authority.AuthorityTreeNodeVo;
import in.hocg.eagle.modules.ums.pojo.vo.role.RoleComplexAndAuthorityVo;
import in.hocg.eagle.modules.ums.service.AccountService;
import in.hocg.eagle.modules.ums.service.RoleAccountService;
import in.hocg.eagle.modules.ums.service.RoleAuthorityService;
import in.hocg.web.AbstractServiceImpl;
import in.hocg.web.SpringContext;
import in.hocg.web.constant.GlobalConstant;
import in.hocg.web.constant.datadict.Enabled;
import in.hocg.web.constant.datadict.Gender;
import in.hocg.web.constant.datadict.Platform;
import in.hocg.web.datastruct.tree.Tree;
import in.hocg.web.env.Env;
import in.hocg.web.exception.ServiceException;
import in.hocg.web.lang.Avatars;
import in.hocg.web.security.TokenUtility;
import in.hocg.web.utils.LangUtils;
import in.hocg.web.utils.ValidUtils;
import in.hocg.web.utils.web.RequestUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>
 * [用户模块] 账号表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AccountServiceImpl extends AbstractServiceImpl<AccountMapper, Account> implements AccountService {

    private final RoleAccountService roleAccountService;
    private final RoleAuthorityService roleAuthorityService;
    private final MailManager mailManager;
    private final RedisManager redisManager;
    private final AccountMapping mapping;
    private final RoleMapping roleMapping;
    private final AuthorityMapping authorityMapping;
    private final PasswordEncoder passwordEncoder;
    private final OssManager ossManager;
    private final SmsManager smsManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IdAccountComplexVo selectOneComplexAndRole(Long id) {
        final Account account = baseMapper.selectById(id);
        ValidUtils.notNull(account, "账号不存在");
        final List<Role> roles = roleAccountService.selectListRoleByAccountId(id, GlobalConstant.CURRENT_PLATFORM.getCode());
        List<Authority> authorities;
        List<RoleComplexAndAuthorityVo> roleComplexes = Lists.newArrayList();
        for (Role role : roles) {
            authorities = roleAuthorityService.selectListAuthorityByRoleIdAndEnabled(role.getId(), Enabled.On.getCode());
            roleComplexes.add(roleMapping.asRoleComplexAndAuthorityVo(role, authorities));
        }
        return mapping.asIdAccountComplexVo(account, roleComplexes);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountComplexVo selectOneComplex(Long id) {
        final Account entity = baseMapper.selectById(id);
        ValidUtils.notNull(entity, "账号不存在");
        return convertComplex(entity);
    }

    @Override
    public Optional<Account> selectOneByUsername(String username) {
        return baseMapper.selectOneByUsername(username);
    }

    public Optional<Account> selectOneByEmail(String email) {
        return lambdaQuery().eq(Account::getEmail, email).oneOpt();
    }

    @Override
    public Optional<Account> selectOneByPhone(String phone) {
        return lambdaQuery().eq(Account::getPhone, phone).oneOpt();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantRole(GrantRoleQo qo) {
        final Long accountId = qo.getId();
        final Integer platform = Platform.Eagle.getCode();
        final List<Long> currentRoleIds = roleAccountService.selectListRoleByAccountId(accountId, platform)
            .stream().map(Role::getId).collect(Collectors.toList());

        final List<Long> roleIds = qo.getRoles();
        // 删除
        for (Long roleId : currentRoleIds) {
            if (!roleIds.contains(roleId)) {
                roleAccountService.deleteByAccountIdAndRoleId(accountId, roleId);
            }
        }

        // 新增
        for (Long roleId : roleIds) {
            if (!currentRoleIds.contains(roleId)) {
                roleAccountService.grantRole(accountId, roleId);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Authority> selectListAuthorityById(Long accountId, Integer platform) {
        List<Authority> authorities = roleAccountService.selectListAuthorityByAccountId(accountId, platform);
        return authorities.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Role> selectListRoleById(Long accountId, Integer platform) {
        return roleAccountService.selectListRoleByAccountId(accountId, platform);
    }

    @Override
    public List<AuthorityTreeNodeVo> selectAuthorityTreeByCurrentAccount(Long accountId, Integer platform) {
        final List<Authority> authorities = selectListAuthorityById(accountId, platform);
        return Tree.getChild(null, authorities.stream()
            .map(authorityMapping::asAuthorityTreeNodeVo)
            .collect(Collectors.toList()));
    }

    @Override
    public IPage<AccountComplexVo> paging(AccountSearchQo qo) {
        return baseMapper.paging(qo, qo.page())
            .convert(this::convertComplex);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(AccountUpdateStatusQo qo) {
        Account entity = mapping.asAccount(qo);
        entity.setLastUpdatedAt(qo.getCreatedAt());
        entity.setLastUpdater(qo.getUserId());
        validUpdateById(entity);
    }

    @Override
    public IPage<AccountComplexVo> pagingWithComplete(AccountCompleteQo qo) {
        return baseMapper.pagingWithComplete(qo, qo.page())
            .convert(this::convertComplex);
    }

    @Override
    public void resetPassword(Long id) {
        final Account entity = getById(id);
        ValidUtils.notNull(entity, "账号不存在");
        final String email = entity.getEmail();
        ValidUtils.notNull(email, "该账号未配置邮箱");
        final String token = "xx";
        redisManager.setResetPasswordToken(email, token, 5);
        String url = String.format("%s/%s", Env.hostname(), token);
        mailManager.sendResetPasswordMail(entity.getNickname(), email, url);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(ChangePasswordQo qo) {
        final String mail = qo.getMail();
        String token = qo.getToken();
        final String newPassword = qo.getNewPassword();
        final boolean isOk = redisManager.validResetPasswordToken(mail, token);
        ValidUtils.isTrue(isOk, "Token 已经失效");
        final Optional<Account> accountOpt = selectOneByEmail(mail);
        if (!accountOpt.isPresent()) {
            ValidUtils.fail(mail + " 未注册");
        }
        final Account update = new Account();
        update.setId(accountOpt.get().getId());
        update.setLastUpdatedAt(qo.getCreatedAt());
        update.setLastUpdater(qo.getUserId());
        update.setPassword(passwordEncoder.encode(newPassword));
        validUpdateById(update);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(@NonNull Long id, @NonNull String newPassword) {
        final Account update = new Account();
        update.setId(id);
        update.setPassword(passwordEncoder.encode(newPassword));
        validUpdateById(update);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String signUp(AccountSignUpQo qo) {
        final LocalDateTime createdAt = qo.getCreatedAt();
        final String phone = qo.getPhone();
        final String smsCode = qo.getSmsCode();
        final String nickname = LangUtils.getOrDefault(qo.getNickname(), phone);
        if (!smsManager.validSmsCode(phone, smsCode)) {
            throw ServiceException.wrap("验证码错误");
        }

        // 账号是否存在
        Account entity = selectOneByPhone(phone)
            .orElseGet(() -> {
                final Account account = new Account();
                final Optional<HttpServletRequest> requestOpt = SpringContext.getRequest();
                requestOpt.ifPresent(request -> account.setCreatedIp(RequestUtils.getClientIP(request)));
                return account.setNickname(nickname)
                    .setGender(Gender.Man.getCode())
                    .setPassword(passwordEncoder.encode(UUID.randomUUID().toString()))
                    .setUsername(phone)
                    .setPhone(phone)
                    .setCreatedAt(createdAt);
            });

        if (Objects.isNull(entity.getId())) {
            validInsert(entity);
        } else {
            throw ServiceException.wrap("该手机号码已注册");
        }

        // 更新头像
        final Long entityId = entity.getId();
        final File file = Avatars.getAvatarAsPath(entityId).toFile();
        final Account update = new Account()
            .setId(entityId)
            .setAvatar(ossManager.uploadToOss(file, file.getName()));
        this.validUpdateById(update);
        return TokenUtility.encode(update.getUsername());
    }

    public void validResetPasswordToken(String email, String token) {
        final boolean isOk = redisManager.validResetPasswordToken(email, token);
        ValidUtils.isTrue(isOk, "Token 已经失效");
    }

    public AccountComplexVo convertComplex(Account entity) {
        return mapping.asAccountComplexVo(entity);
    }

    @Override
    public void validEntity(Account entity) {
        final Long creatorId = entity.getCreator();
        final Long lastUpdaterId = entity.getLastUpdater();
        if (Objects.nonNull(creatorId)) {
            ValidUtils.notNull(baseMapper.selectById(creatorId));
        }
        if (Objects.nonNull(lastUpdaterId)) {
            ValidUtils.notNull(baseMapper.selectById(lastUpdaterId));
        }
    }
}
