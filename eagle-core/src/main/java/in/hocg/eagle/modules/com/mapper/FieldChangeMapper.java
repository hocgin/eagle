package in.hocg.eagle.modules.com.mapper;

import in.hocg.eagle.modules.com.entity.FieldChange;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [基础模块] 业务日志-字段变更记录表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-04-11
 */
@Mapper
public interface FieldChangeMapper extends BaseMapper<FieldChange> {

    List<FieldChange> selectAllByChangeLogId(@Param("changeLogId") Long changeLogId);
}
