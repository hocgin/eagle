package in.hocg.eagle.modules.notify.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.mapstruct.qo.notify.SearchNotifyPageQo;
import in.hocg.eagle.modules.notify.entity.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    IPage<Notification> search(@Param("qo") SearchNotifyPageQo qo, @Param("page") Page page);

}
