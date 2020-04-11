package in.hocg.eagle.modules.com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.com.entity.ChangeLog;
import in.hocg.eagle.modules.com.pojo.qo.changelog.ChangeLogPagingQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [基础模块] 业务操作日志表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-04-11
 */
@Mapper
public interface ChangeLogMapper extends BaseMapper<ChangeLog> {

    IPage<ChangeLog> pagingWithComplex(@Param("qo") ChangeLogPagingQo qo, @Param("page") Page page);
}
