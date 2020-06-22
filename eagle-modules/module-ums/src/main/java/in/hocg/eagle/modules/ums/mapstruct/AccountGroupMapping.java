package in.hocg.eagle.modules.ums.mapstruct;

import in.hocg.eagle.modules.ums.entity.AccountGroup;
import in.hocg.eagle.modules.ums.pojo.qo.account.group.AccountGroupSaveQo;
import in.hocg.eagle.modules.ums.pojo.vo.account.group.AccountGroupComplexVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/2/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface AccountGroupMapping {


    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "creator", ignore = true)
    AccountGroup asAccountGroup(AccountGroupSaveQo qo);

    @Mapping(target = "memberSourceName", ignore = true)
    @Mapping(target = "lastUpdaterName", ignore = true)
    @Mapping(target = "groupTypeName", ignore = true)
    @Mapping(target = "creatorName", ignore = true)
    AccountGroupComplexVo asAccountGroupComplexVo(AccountGroup entity);
}
