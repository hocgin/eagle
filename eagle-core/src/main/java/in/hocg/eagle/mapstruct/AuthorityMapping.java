package in.hocg.eagle.mapstruct;

import in.hocg.eagle.mapstruct.qo.AuthorityPostQo;
import in.hocg.eagle.mapstruct.qo.AuthorityPutQo;
import in.hocg.eagle.mapstruct.vo.AuthorityTreeNodeVo;
import in.hocg.eagle.modules.account.entity.Authority;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
    Authority asAuthority(AuthorityPostQo qo);
    
    /**
     * AuthorityPutQo -> Authority
     *
     * @param qo
     * @return
     */
    @Mapping(target = "treePath", ignore = true)
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Authority asAuthority(AuthorityPutQo qo);
    
    /**
     * Authority -> AuthorityTreeNodeVo
     *
     * @param authority
     * @return
     */
    @Mapping(target = "children", ignore = true)
    AuthorityTreeNodeVo asAuthorityTreeNodeVo(Authority authority);
}
