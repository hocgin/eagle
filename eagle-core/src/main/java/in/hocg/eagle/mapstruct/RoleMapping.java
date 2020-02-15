package in.hocg.eagle.mapstruct;


import in.hocg.eagle.mapstruct.qo.RolePostQo;
import in.hocg.eagle.mapstruct.qo.RolePutQo;
import in.hocg.eagle.modules.account.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/2/11.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface RoleMapping {
    
    /**
     * RolePostQo -> Role
     *
     * @param qo
     * @return
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Role asRole(RolePostQo qo);
    
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    Role asRole(RolePutQo qo);
}
