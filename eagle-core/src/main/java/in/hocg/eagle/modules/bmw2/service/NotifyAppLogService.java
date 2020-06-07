package in.hocg.eagle.modules.bmw2.service;

import in.hocg.eagle.modules.bmw2.entity.NotifyAppLog;
import in.hocg.eagle.basic.AbstractService;

import java.util.List;

/**
 * <p>
 * [支付网关] 所有通知应用方日志表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
public interface NotifyAppLogService extends AbstractService<NotifyAppLog> {

    List<NotifyAppLog> selectListByNotifyAppId(Long notifyId);
}
