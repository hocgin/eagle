package in.hocg.eagle.mapstruct;

import in.hocg.eagle.mapstruct.qo.AuthorityPostQo;
import in.hocg.eagle.modules.account.entity.Authority;
import org.mapstruct.Mapper;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface AuthorityMapping {
    
    Authority asAuthority(AuthorityPostQo qo);
    
}
