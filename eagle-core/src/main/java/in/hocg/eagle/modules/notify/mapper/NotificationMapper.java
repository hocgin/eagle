package in.hocg.eagle.modules.notify.mapper;

import in.hocg.eagle.modules.notify.entity.Notification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [消息模块] 通知-接收人表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-03-04
 */
@Mapper
public interface NotificationMapper extends BaseMapper<Notification> {

}
