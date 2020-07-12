package in.hocg.eagle.modules.ums.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractService;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.modules.ums.entity.AccountAddress;
import in.hocg.eagle.modules.ums.pojo.qo.account.address.AccountAddressPageQo;
import in.hocg.eagle.modules.ums.pojo.qo.account.address.AccountAddressSaveQo;
import in.hocg.eagle.modules.ums.pojo.vo.account.address.AccountAddressComplexVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * <p>
 * [用户模块] 收货地址表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
public interface AccountAddressService extends AbstractService<AccountAddress> {


    @Transactional(rollbackFor = Exception.class)
    void saveOne(Long id, AccountAddressSaveQo qo);

    IPage<AccountAddressComplexVo> paging(AccountAddressPageQo qo);

    void deleteOne(IdRo qo);

    void insertMyAddress(AccountAddressSaveQo qo);

    void updateMyAddress(Long id, AccountAddressSaveQo qo);

    IPage<AccountAddressComplexVo> pagingMyAddress(AccountAddressPageQo qo);

    void deleteMyAddress(IdRo qo);

    Optional<AccountAddressComplexVo> getDefaultByUserId(Long userId);

    AccountAddressComplexVo getMyAddress(IdRo ro);
}
