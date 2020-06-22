package in.hocg.eagle.modules.wx.service;

import in.hocg.eagle.modules.wx.pojo.qo.message.SendMessageToGroupQo;
import in.hocg.eagle.modules.wx.pojo.qo.message.SendMessageToUserQo;
import in.hocg.eagle.modules.wx.pojo.qo.message.SendPreviewMessageToUserQo;
import in.hocg.eagle.modules.wx.pojo.qo.message.SendTemplateMessageToUserQo;

/**
 * Created by hocgin on 2020/5/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface WxMpMessageService {
    void sendMessageToUser(SendMessageToUserQo qo);

    void sendMessageToGroup(SendMessageToGroupQo qo);

    void sendTemplateMessageToUser(SendTemplateMessageToUserQo qo);

    void sendPreviewMessageToUser(SendPreviewMessageToUserQo qo);
}
