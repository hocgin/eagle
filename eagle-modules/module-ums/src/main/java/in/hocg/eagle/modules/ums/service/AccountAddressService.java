package in.hocg.eagle.modules.ums.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.web.AbstractService;
import in.hocg.web.pojo.qo.IdQo;
import in.hocg.eagle.modules.ums.entity.AccountAddress;
import in.hocg.eagle.modules.ums.pojo.qo.account.address.AccountAddressPageQo;
import in.hocg.eagle.modules.ums.pojo.qo.account.address.AccountAddressSaveQo;
import in.hocg.eagle.modules.ums.pojo.vo.account.address.AccountAddressComplexVo;

/**
 * <p>
 * [用户模块] 收货地址表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
public interface AccountAddressService extends AbstractService<AccountAddress> {

    void saveOne(AccountAddressSaveQo qo);

    IPage<AccountAddressComplexVo> paging(AccountAddressPageQo qo);

    void deleteOne(IdQo qo);
}
