package in.hocg.eagle.mapstruct;


import in.hocg.eagle.mapstruct.vo.account.AccountSearchVo;
import in.hocg.eagle.mapstruct.vo.account.IdAccountComplexVo;
import in.hocg.eagle.mapstruct.vo.role.RoleComplexVo;
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
    default IdAccountComplexVo asIdAccountComplexVo(Account account, List<RoleComplexVo> roles) {
        final IdAccountComplexVo result = asIdAccountComplexVo(account);
        result.setRoles(roles);
        return result;
    }

    AccountSearchVo asAccountSearchVo(Account account);
}
