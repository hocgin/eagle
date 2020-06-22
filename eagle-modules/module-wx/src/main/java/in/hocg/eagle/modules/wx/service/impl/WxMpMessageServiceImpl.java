package in.hocg.eagle.modules.wx.service.impl;

import in.hocg.eagle.modules.wx.entity.WxUser;
import in.hocg.eagle.modules.wx.manager.WxMpManager;
import in.hocg.eagle.modules.wx.pojo.qo.message.SendMessageToGroupQo;
import in.hocg.eagle.modules.wx.pojo.qo.message.SendMessageToUserQo;
import in.hocg.eagle.modules.wx.pojo.qo.message.SendPreviewMessageToUserQo;
import in.hocg.eagle.modules.wx.pojo.qo.message.SendTemplateMessageToUserQo;
import in.hocg.eagle.modules.wx.service.WxMpMessageService;
import in.hocg.eagle.modules.wx.service.WxUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by hocgin on 2020/5/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class WxMpMessageServiceImpl implements WxMpMessageService {
    private final WxMpManager wxMpManager;
    private final WxUserService wxUserService;

    @Override
    public void sendMessageToUser(SendMessageToUserQo qo) {
        final List<Long> users = qo.getToUsers();
        final Map<String, List<WxUser>> userGroup = wxUserService.selectListById(users).parallelStream()
            .collect(Collectors.groupingBy(WxUser::getAppid));
        final String msgType = qo.getMsgType();
        final String mediaId = qo.getMediaId();
        final String content = qo.getContent();

        for (Map.Entry<String, List<WxUser>> entry : userGroup.entrySet()) {
            final String appid = entry.getKey();
            final List<WxUser> toUsers = entry.getValue();
            final List<String> openId = toUsers.parallelStream().map(WxUser::getOpenid).collect(Collectors.toList());
            wxMpManager.massOpenIdsMessageSend(appid, openId, msgType, mediaId, content);
        }

    }

    @Override
    public void sendMessageToGroup(SendMessageToGroupQo qo) {
        final String appid = qo.getAppid();
        final Long tagId = qo.getTagId();
        final String msgType = qo.getMsgType();
        final String mediaId = qo.getMediaId();
        final String content = qo.getContent();

        wxMpManager.massGroupMessageSend(appid, tagId, msgType, mediaId, content);
    }

    @Override
    public void sendTemplateMessageToUser(SendTemplateMessageToUserQo qo) {
        final List<Long> users = qo.getToUsers();
        final Map<String, List<WxUser>> userGroup = wxUserService.selectListById(users).parallelStream()
            .collect(Collectors.groupingBy(WxUser::getAppid));
        final String templateId = qo.getTemplateId();
        final List<SendTemplateMessageToUserQo.TemplateData> templateData = qo.getData();
        final String url = qo.getUrl();
        final SendTemplateMessageToUserQo.MiniProgram miniProgram = qo.getMiniProgram();

        for (Map.Entry<String, List<WxUser>> entry : userGroup.entrySet()) {
            final String appid = entry.getKey();
            final List<WxUser> toUsers = entry.getValue();
            final List<String> openIds = toUsers.parallelStream().map(WxUser::getOpenid).collect(Collectors.toList());
            for (String openId : openIds) {
                wxMpManager.sendTemplateMsg(appid, openId, templateId, templateData, url, miniProgram);
            }
        }
    }

    @Override
    public void sendPreviewMessageToUser(SendPreviewMessageToUserQo qo) {
        final List<Long> users = qo.getToUsers();
        final Map<String, List<WxUser>> userGroup = wxUserService.selectListById(users).parallelStream()
            .collect(Collectors.groupingBy(WxUser::getAppid));
        final String msgType = qo.getMsgType();
        final String mediaId = qo.getMediaId();
        final String content = qo.getContent();

        for (Map.Entry<String, List<WxUser>> entry : userGroup.entrySet()) {
            final String appid = entry.getKey();
            final List<WxUser> toUsers = entry.getValue();
            final List<String> openIds = toUsers.parallelStream().map(WxUser::getOpenid).collect(Collectors.toList());
            for (String openId : openIds) {
                wxMpManager.massMessagePreview(appid, openId, msgType, mediaId, content);
            }
        }
    }
}
