package in.hocg.eagle.modules.ums.mapstruct;

import in.hocg.eagle.modules.ums.entity.Account;
import in.hocg.eagle.modules.ums.pojo.vo.account.group.AccountGroupMemberComplexVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/2/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface AccountGroupMemberMapping {


    @Mapping(target = "expiredName", ignore = true)
    @Mapping(target = "genderName", ignore = true)
    @Mapping(target = "lockedName", ignore = true)
    @Mapping(target = "enabledName", ignore = true)
    @Mapping(target = "accountName", source = "entity.nickname")
    @Mapping(target = "accountId", source = "entity.id")
    AccountGroupMemberComplexVo asAccountGroupMemberComplexVo(Account entity);
}
