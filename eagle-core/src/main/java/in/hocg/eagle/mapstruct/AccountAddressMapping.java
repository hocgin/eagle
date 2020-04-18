package in.hocg.eagle.mapstruct;

import in.hocg.eagle.modules.ums.entity.AccountAddress;
import in.hocg.eagle.modules.ums.pojo.qo.account.address.AccountAddressSaveQo;
import in.hocg.eagle.modules.ums.pojo.vo.account.address.AccountAddressComplexVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/2/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface AccountAddressMapping {


    @Mapping(target = "accountId", ignore = true)
    AccountAddress asAccountAddress(AccountAddressSaveQo qo);

    AccountAddressComplexVo asAccountAddressComplexVo(AccountAddress entity);
}
