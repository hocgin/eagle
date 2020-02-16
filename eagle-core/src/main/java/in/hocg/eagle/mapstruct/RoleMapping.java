package in.hocg.eagle.mapstruct;


import in.hocg.eagle.basic.security.GrantedAuthority;
import in.hocg.eagle.mapstruct.qo.RolePostQo;
import in.hocg.eagle.mapstruct.qo.RolePutQo;
import in.hocg.eagle.mapstruct.vo.RoleComplexVo;
import in.hocg.eagle.modules.account.entity.Authority;
import in.hocg.eagle.modules.account.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
    
    
    List<GrantedAuthority> asGrantedAuthority(List<Role> roles);
    
    default SimpleGrantedAuthority asGrantedAuthority(Role role) {
        return new SimpleGrantedAuthority("ROLE_" + role.getRoleCode());
    }
    
}
