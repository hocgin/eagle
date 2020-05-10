package in.hocg.eagle.mapstruct;

import in.hocg.eagle.basic.security.GrantedAuthority;
import in.hocg.eagle.modules.ums.pojo.qo.authority.AuthorityInsertQo;
import in.hocg.eagle.modules.ums.pojo.qo.authority.AuthorityUpdateQo;
import in.hocg.eagle.modules.ums.pojo.vo.authority.AuthorityComplexAndRoleVo;
import in.hocg.eagle.modules.ums.pojo.vo.authority.AuthorityTreeNodeVo;
import in.hocg.eagle.modules.ums.entity.Authority;
import in.hocg.eagle.modules.ums.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface AuthorityMapping {


    /**
     * AuthorityPostQo -> Authority
     *
     * @param qo
     * @return
     */
    @Mapping(target = "treePath", ignore = true)
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Authority asAuthority(AuthorityInsertQo qo);

    /**
     * AuthorityPutQo -> Authority
     *
     * @param qo
     * @return
     */
    @Mapping(target = "platform", ignore = true)
    @Mapping(target = "treePath", ignore = true)
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Authority asAuthority(AuthorityUpdateQo qo);

    /**
     * Authority -> AuthorityTreeNodeVo
     *
     * @param authority
     * @return
     */
    @Mapping(target = "children", ignore = true)
    AuthorityTreeNodeVo asAuthorityTreeNodeVo(Authority authority);

    /**
     * Authority -> GrantedAuthority
     *
     * @param authority
     * @return
     */
    default GrantedAuthority asGrantedAuthority(Authority authority) {
        final GrantedAuthority grantedAuthority = new GrantedAuthority();
        grantedAuthority.setAuthority(authority.getAuthorityCode());
        return grantedAuthority;
    }

    /**
     * Collection<Authority> -> List<GrantedAuthority>
     *
     * @param authorities
     * @return
     */
    List<GrantedAuthority> asGrantedAuthority(Collection<Authority> authorities);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "parentName", ignore = true)
    @Mapping(target = "typeName", ignore = true)
    @Mapping(target = "platformName", ignore = true)
    @Mapping(target = "lastUpdaterName", ignore = true)
    @Mapping(target = "enabledName", ignore = true)
    @Mapping(target = "creatorName", ignore = true)
    AuthorityComplexAndRoleVo asAuthorityComplexVo(Authority authority);

    default AuthorityComplexAndRoleVo asAuthorityComplexVo(Authority authority, List<Role> roles) {
        final AuthorityComplexAndRoleVo result = this.asAuthorityComplexVo(authority);
        result.setRoles(roles);
        return result;
    }
}
