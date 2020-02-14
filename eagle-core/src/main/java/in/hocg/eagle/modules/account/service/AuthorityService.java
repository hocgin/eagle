package in.hocg.eagle.modules.account.service;

import in.hocg.eagle.mapstruct.qo.AuthorityPostQo;
import in.hocg.eagle.mapstruct.qo.AuthorityPutQo;
import in.hocg.eagle.modules.account.entity.Authority;
import in.hocg.eagle.basic.AbstractService;

/**
 * <p>
 * [用户模块] 权限表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
public interface AuthorityService extends AbstractService<Authority> {
    
    void insertOne(AuthorityPostQo qo);
    
    void updateOne(AuthorityPutQo qo);
}
