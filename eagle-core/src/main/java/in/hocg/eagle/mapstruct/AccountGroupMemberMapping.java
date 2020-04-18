package in.hocg.eagle.mapstruct;

import in.hocg.eagle.modules.ums.entity.AccountGroupMember;
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


    @Mapping(target = "accountName", ignore = true)
    @Mapping(target = "creatorName", ignore = true)
    AccountGroupMemberComplexVo asAccountGroupMemberComplexVo(AccountGroupMember entity);
}
