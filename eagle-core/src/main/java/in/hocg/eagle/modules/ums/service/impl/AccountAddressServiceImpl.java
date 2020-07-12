package in.hocg.eagle.modules.ums.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.constant.datadict.IsDefault;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractServiceImpl;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.modules.ums.entity.AccountAddress;
import in.hocg.eagle.modules.ums.mapper.AccountAddressMapper;
import in.hocg.eagle.modules.ums.mapstruct.AccountAddressMapping;
import in.hocg.eagle.modules.ums.pojo.qo.account.address.AccountAddressPageQo;
import in.hocg.eagle.modules.ums.pojo.qo.account.address.AccountAddressSaveQo;
import in.hocg.eagle.modules.ums.pojo.vo.account.address.AccountAddressComplexVo;
import in.hocg.eagle.modules.ums.service.AccountAddressService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * [用户模块] 收货地址表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AccountAddressServiceImpl extends AbstractServiceImpl<AccountAddressMapper, AccountAddress> implements AccountAddressService {
    private final AccountAddressMapping mapping;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(Long id, AccountAddressSaveQo qo) {
        final Long userId = qo.getUserId();
        AccountAddress entity = mapping.asAccountAddress(qo);
        if (Objects.isNull(id)) {
            entity.setAccountId(userId);
        } else {
            entity.setId(id);
        }

        if (LangUtils.equals(IsDefault.On.getCode(), qo.getIsDefault())) {
            updateAllUnDefaultByAccountId(userId);
        }

        validInsertOrUpdate(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertMyAddress(AccountAddressSaveQo qo) {
        this.saveOne(null, qo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMyAddress(Long id, AccountAddressSaveQo qo) {
        final AccountAddress accountAddress = this.getById(id);
        ValidUtils.isTrue(LangUtils.equals(accountAddress.getAccountId(), qo.getUserId()), "操作失败");
        this.saveOne(id, qo);
    }

    @Override
    @ApiOperation("分页查询我的收货地址 - 收货地址")
    @Transactional(rollbackFor = Exception.class)
    public IPage<AccountAddressComplexVo> pagingMyAddress(AccountAddressPageQo qo) {
        qo.setAccountId(qo.getUserId());
        return this.paging(qo);
    }

    @Override
    @ApiOperation("删除我的收货地址 - 收货地址")
    @Transactional(rollbackFor = Exception.class)
    public void deleteMyAddress(IdRo qo) {
        final Long id = qo.getId();
        final AccountAddress accountAddress = this.getById(id);
        ValidUtils.notNull(accountAddress, "未找到收货地址");
        ValidUtils.isTrue(LangUtils.equals(accountAddress.getAccountId(), qo.getUserId()), "操作失败");
        this.deleteOne(qo);
    }

    @Override
    @ApiOperation("获取默认收货地址 - 收货地址")
    @Transactional(rollbackFor = Exception.class)
    public Optional<AccountAddressComplexVo> getDefaultByUserId(Long userId) {
        return baseMapper.getDefaultByUserId(userId).map(this::convertComplex);
    }

    @Override
    @ApiOperation("获取我的收货地址 - 收货地址")
    @Transactional(rollbackFor = Exception.class)
    public AccountAddressComplexVo getMyAddress(IdRo ro) {
        final Long id = ro.getId();
        final AccountAddress entity = getById(id);
        ValidUtils.notNull(entity, "未找到收货地址");
        ValidUtils.isTrue(LangUtils.equals(entity.getAccountId(), ro.getUserId()), "操作失败");
        return convertComplex(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<AccountAddressComplexVo> paging(AccountAddressPageQo qo) {
        return baseMapper.paging(qo, qo.page())
            .convert(this::convertComplex);
    }

    private AccountAddressComplexVo convertComplex(AccountAddress entity) {
        return mapping.asAccountAddressComplexVo(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOne(IdRo qo) {
        removeById(qo.getId());
    }

    public void updateAllUnDefaultByAccountId(Long accountId) {
        baseMapper.updateAllUnDefaultByAccountId(accountId);
    }

}
