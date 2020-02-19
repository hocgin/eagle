package in.hocg.eagle.modules.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import in.hocg.eagle.modules.account.entity.Authority;
import in.hocg.eagle.modules.account.entity.AuthorityAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [用户模块] 权限-账号表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Mapper
public interface AuthorityAccountMapper extends BaseMapper<AuthorityAccount> {
    
    Integer selectListByAuthorityRegexTreePath(@Param("regexTreePath") String regexTreePath);
    
    /**
     * 查找账号所具备的权限
     *
     * @param accountId
     * @return
     */
    List<Authority> selectListAuthorityByAccountId(@Param("accountId") Long accountId);
}
