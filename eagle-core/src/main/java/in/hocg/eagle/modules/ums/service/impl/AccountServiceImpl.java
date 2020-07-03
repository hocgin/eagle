package in.hocg.eagle.modules.ums.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import in.hocg.eagle.basic.constant.GlobalConstant;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.constant.datadict.Gender;
import in.hocg.eagle.basic.constant.datadict.Platform;
import in.hocg.eagle.basic.datastruct.tree.Tree;
import in.hocg.eagle.basic.env.Env;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.basic.ext.lang.Avatars;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractServiceImpl;
import in.hocg.eagle.basic.ext.security.authentication.token.TokenUtility;
import in.hocg.eagle.basic.ext.web.SpringContext;
import in.hocg.eagle.manager.mail.MailManager;
import in.hocg.eagle.manager.oss.OssManager;
import in.hocg.eagle.manager.redis.RedisManager;
import in.hocg.eagle.manager.sms.SmsManager;
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
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import in.hocg.eagle.utils.web.RequestUtils;
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
import java.util.function.BiFunction;
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
    public Optional<String> getAvatarUrlByUsername(String username) {
        if (Strings.isNullOrEmpty(username)) {
            return Optional.empty();
        }
        final Optional<Account> accountOpt = this.selectOneByUsername(username);
        return accountOpt.map(Account::getAvatar);
    }

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
    public AccountComplexVo selectOne(Long id) {
        final Account entity = baseMapper.selectById(id);
        ValidUtils.notNull(entity, "账号不存在");
        return convertComplex(entity);
    }

    @Override
    public Optional<Account> selectOneByUsername(String username) {
        return baseMapper.selectOneByUsername(username);
    }

    @Override
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
        final List<Long> entities = qo.getRoles();

        final BiFunction<Long, Long, Boolean> isSame = LangUtils::equals;
        final List<Long> allData = roleAccountService.selectListRoleByAccountId(accountId, platform)
            .parallelStream().map(Role::getId).collect(Collectors.toList());
        final List<Long> mixedList = LangUtils.getMixed(allData, entities, isSame);
        List<Long> deleteList = LangUtils.removeIfExits(allData, mixedList, isSame);
        List<Long> addList = LangUtils.removeIfExits(entities, mixedList, isSame);

        // 删除
        deleteList.forEach(id -> roleAccountService.deleteByAccountIdAndRoleId(accountId, id));

        // 新增
        addList.forEach(id -> roleAccountService.grantRole(accountId, id));
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
    public void changePasswordUseMail(ChangePasswordUseMailRo qo) {
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
    public String changePasswordUseSmsCode(ChangePasswordUseSmsCodeQo ro) {
        final String phone = ro.getPhone();
        final String smsCode = ro.getSmsCode();
        final String password = ro.getPassword();
        if (!smsManager.validSmsCode(phone, smsCode)) {
            throw ServiceException.wrap("验证码错误");
        }
        final Optional<Account> accountOpt = this.selectOneByPhone(phone);
        if (!accountOpt.isPresent()) {
            throw ServiceException.wrap("找不到账号");
        }
        final Account account = accountOpt.get();
        if (!LangUtils.equals(Enabled.On.getCode(), account.getEnabled())) {
            throw ServiceException.wrap("账号被禁用了");
        }

        final Account update = new Account();
        update.setId(account.getId());
        update.setPassword(passwordEncoder.encode(password));
        validUpdateById(update);
        return TokenUtility.encode(account.getUsername());
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
        return TokenUtility.encode(entity.getUsername());
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
