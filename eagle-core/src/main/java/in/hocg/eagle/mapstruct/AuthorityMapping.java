package in.hocg.eagle.mapstruct;

import in.hocg.eagle.mapstruct.qo.AuthorityPostQo;
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
     * @param qo
     * @return
     */
    @Mapping(target = "treePath", ignore = true)
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Authority asAuthority(AuthorityPostQo qo);
    
}
