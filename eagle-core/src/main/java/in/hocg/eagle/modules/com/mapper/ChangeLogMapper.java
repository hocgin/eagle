package in.hocg.eagle.modules.com.mapper;

import in.hocg.eagle.modules.com.entity.ChangeLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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

}
