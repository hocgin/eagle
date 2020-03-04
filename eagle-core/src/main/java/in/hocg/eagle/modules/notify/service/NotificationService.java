package in.hocg.eagle.modules.notify.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.mapstruct.qo.notify.SearchNotifyPageQo;
import in.hocg.eagle.mapstruct.vo.notify.SearchNotifyVo;
import in.hocg.eagle.modules.notify.entity.Notification;

/**
 * <p>
 * [消息模块] 通知-接收人表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-04
 */
public interface NotificationService extends AbstractService<Notification> {

    IPage<SearchNotifyVo> search(SearchNotifyPageQo qo);
}
