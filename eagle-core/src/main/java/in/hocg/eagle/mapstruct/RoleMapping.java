package in.hocg.eagle.mapstruct;


import in.hocg.eagle.basic.security.GrantedAuthority;
import in.hocg.eagle.mapstruct.qo.role.RoleInsertQo;
import in.hocg.eagle.mapstruct.qo.role.RoleUpdateQo;
import in.hocg.eagle.mapstruct.vo.role.RoleComplexAndAuthorityVo;
import in.hocg.eagle.mapstruct.vo.role.RoleComplexVo;
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
    Role asRole(RoleInsertQo qo);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    Role asRole(RoleUpdateQo qo);

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
    RoleComplexAndAuthorityVo asRoleComplexAndAuthorityVo(Role role);

    /**
     * Authority -> RoleComplexVo.AuthorityVo
     *
     * @param authority
     * @return
     */
    RoleComplexAndAuthorityVo.AuthorityVo asRoleComplexVo$AuthorityVo(Authority authority);

    /**
     * List<Authority> -> List<RoleComplexVo.AuthorityVo>
     *
     * @param authorities
     * @return
     */
    List<RoleComplexAndAuthorityVo.AuthorityVo> asRoleComplexVo$AuthorityVo(List<Authority> authorities);

    /**
     * Role role, List<Authority> authorities -> RoleComplexVo
     *
     * @param role
     * @param authorities
     * @return
     */
    default RoleComplexAndAuthorityVo asRoleComplexAndAuthorityVo(Role role, List<Authority> authorities) {
        RoleComplexAndAuthorityVo result = asRoleComplexAndAuthorityVo(role);
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

    @Mapping(target = "platformName", ignore = true)
    @Mapping(target = "enabledName", ignore = true)
    RoleComplexVo asRoleComplexVo(Role entity);
}
