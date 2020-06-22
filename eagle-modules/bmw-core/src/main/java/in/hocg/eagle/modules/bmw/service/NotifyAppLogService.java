package in.hocg.eagle.modules.bmw.service;

import in.hocg.eagle.modules.bmw.entity.NotifyAppLog;
import in.hocg.web.AbstractService;

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
