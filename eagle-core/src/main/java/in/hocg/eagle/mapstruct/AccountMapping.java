package in.hocg.eagle.mapstruct;


import in.hocg.eagle.mapstruct.qo.account.AccountUpdateStatusQo;
import in.hocg.eagle.mapstruct.vo.account.AccountComplexVo;
import in.hocg.eagle.mapstruct.vo.account.AccountSearchVo;
import in.hocg.eagle.mapstruct.vo.account.IdAccountComplexVo;
import in.hocg.eagle.mapstruct.vo.role.RoleComplexAndAuthorityVo;
import in.hocg.eagle.modules.account.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Created by hocgin on 2020/2/11.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface AccountMapping {

    /**
     * Account -> IdAccountComplexVo
     *
     * @param account
     * @return
     */
    @Mapping(target = "lockedName", ignore = true)
    @Mapping(target = "expiredName", ignore = true)
    @Mapping(target = "enabledName", ignore = true)
    @Mapping(target = "genderName", ignore = true)
    @Mapping(target = "roles", ignore = true)
    IdAccountComplexVo asIdAccountComplexVo(Account account);

    /**
     * Account account, List<RoleComplexVo> roles -> IdAccountComplexVo
     *
     * @param account
     * @param roles
     * @return
     */
    default IdAccountComplexVo asIdAccountComplexVo(Account account, List<RoleComplexAndAuthorityVo> roles) {
        final IdAccountComplexVo result = asIdAccountComplexVo(account);
        result.setRoles(roles);
        return result;
    }

    AccountSearchVo asAccountSearchVo(Account account);

    @Mapping(target = "genderName", ignore = true)
    AccountComplexVo asAccountComplexVo(Account account);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "nickname", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createdIp", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    Account asAccount(AccountUpdateStatusQo qo);
}
