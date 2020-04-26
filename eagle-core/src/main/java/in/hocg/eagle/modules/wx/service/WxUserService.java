package in.hocg.eagle.modules.wx.service;

import in.hocg.eagle.modules.wx.entity.WxUser;
import in.hocg.eagle.basic.AbstractService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 微信用户表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-25
 */
public interface WxUserService extends AbstractService<WxUser> {
    void unsubscribe(String appid, String openid);

}
