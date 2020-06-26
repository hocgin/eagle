package in.hocg.eagle.modules.ums.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.IsDefault;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.modules.ums.mapstruct.AccountAddressMapping;
import in.hocg.eagle.modules.ums.entity.AccountAddress;
import in.hocg.eagle.modules.ums.mapper.AccountAddressMapper;
import in.hocg.eagle.modules.ums.pojo.qo.account.address.AccountAddressPageQo;
import in.hocg.eagle.modules.ums.pojo.qo.account.address.AccountAddressSaveQo;
import in.hocg.eagle.modules.ums.pojo.vo.account.address.AccountAddressComplexVo;
import in.hocg.eagle.modules.ums.service.AccountAddressService;
import in.hocg.eagle.utils.LangUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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
    public void saveOne(AccountAddressSaveQo qo) {
        final Long userId = qo.getUserId();
        AccountAddress entity = mapping.asAccountAddress(qo);
        final Long id = qo.getId();
        if (Objects.isNull(id)) {
            entity.setAccountId(userId);
        }

        if (LangUtils.equals(IsDefault.On.getCode(), qo.getIsDefault())) {
            updateAllUnDefaultByAccountId(userId);
        }

        validInsertOrUpdate(entity);
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
