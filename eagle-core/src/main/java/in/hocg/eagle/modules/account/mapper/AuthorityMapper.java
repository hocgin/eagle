package in.hocg.eagle.modules.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import in.hocg.eagle.modules.account.pojo.qo.authority.AuthoritySearchQo;
import in.hocg.eagle.modules.account.entity.Authority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [用户模块] 权限表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Mapper
public interface AuthorityMapper extends BaseMapper<Authority> {

    /**
     * 查找 parent_id 符合的数据
     *
     * @param qo
     * @return
     */
    List<Authority> search(@Param("qo") AuthoritySearchQo qo);

    /**
     * 获取 tree_path 匹配的数据
     *
     * @param regexTreePath
     * @return
     */
    List<Authority> selectListByRegexTreePath(@Param("regexTreePath") String regexTreePath);

    /**
     * 删除 tree_path 匹配的数据
     *
     * @param regexTreePath
     * @return
     */
    int deleteListByRegexTreePath(String regexTreePath);
}
