package in.hocg.eagle.mapstruct;


import in.hocg.eagle.basic.security.GrantedAuthority;
import in.hocg.eagle.mapstruct.qo.RolePostQo;
import in.hocg.eagle.mapstruct.qo.RolePutQo;
import in.hocg.eagle.mapstruct.vo.RoleComplexVo;
import in.hocg.eagle.modules.account.entity.Authority;
import in.hocg.eagle.modules.account.entity.Role;
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
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    Role asRole(RolePutQo qo);
    
    @Mapping(target = "title", source = "role.title")
    @Mapping(target = "roleCode", source = "role.roleCode")
    @Mapping(target = "remark", source = "role.remark")
    @Mapping(target = "id", source = "role.id")
    @Mapping(target = "enabled", source = "role.enabled")
    @Mapping(target = "platform", source = "role.platform")
    @Mapping(target = "creator", source = "role.creator")
    @Mapping(target = "createdAt", source = "role.createdAt")
    @Mapping(target = "lastUpdater", source = "role.lastUpdater")
    @Mapping(target = "lastUpdatedAt", source = "role.lastUpdatedAt")
    RoleComplexVo asRoleComplexVo(Role role);
    
    /**
     * Authority -> RoleComplexVo.AuthorityVo
     *
     * @param authority
     * @return
     */
    RoleComplexVo.AuthorityVo asRoleComplexVo$AuthorityVo(Authority authority);
    
    /**
     * List<Authority> -> List<RoleComplexVo.AuthorityVo>
     *
     * @param authorities
     * @return
     */
    List<RoleComplexVo.AuthorityVo> asRoleComplexVo$AuthorityVo(List<Authority> authorities);
    
    /**
     * Role role, List<Authority> authorities -> RoleComplexVo
     *
     * @param role
     * @param authorities
     * @return
     */
    default RoleComplexVo asRoleComplexVo(Role role, List<Authority> authorities) {
        RoleComplexVo result = asRoleComplexVo(role);
        result.setAuthorities(asRoleComplexVo$AuthorityVo(authorities));
        return result;
    }
    
    /**
     * List<Role> -> List<GrantedAuthority>
     *
     * @param roles
     * @return
     */
    List<GrantedAuthority> asGrantedAuthority(List<Role> roles);
    
    
    /**
     * Role -> SimpleGrantedAuthority
     *
     * @param role
     * @return
     */
    default GrantedAuthority asGrantedAuthority(Role role) {
        return new GrantedAuthority()
                .setAuthority("ROLE_" + role.getRoleCode());
    }
    
}
