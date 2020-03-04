package in.hocg.eagle.modules.notify.mapper;

import in.hocg.eagle.modules.notify.entity.Notify;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [消息模块] 通知表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-03-04
 */
@Mapper
public interface NotifyMapper extends BaseMapper<Notify> {

}
