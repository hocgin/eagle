package in.hocg.eagle.modules.wx.pojo.qo.message;

import in.hocg.eagle.basic.pojo.qo.BaseQo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

/**
 * Created by hocgin on 2020/5/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SendTemplateMessageToUserQo extends BaseQo {
    @ApiModelProperty("微信用户ID")
    @Size(min = 1, message = "接收人不能为空")
    private List<Long> toUsers;
    @NotBlank(message = "模版ID不能为空")
    private String templateId;
    @Valid
    @Size(message = "模版参数不能为空")
    private List<TemplateData> data = Collections.emptyList();
    @Valid
    private MiniProgram miniProgram;
    private String url;

    @Data
    @ApiModel("模版参数::变量")
    public static class TemplateData {
        @NotBlank(message = "变量名称不能为空")
        private String name;
        @NotBlank(message = "变量值不能为空")
        private String value;
        private String color;

        public WxMpTemplateData asWxMpTemplateData() {
            return new WxMpTemplateData(name, value, color);
        }
    }

    @Data
    @ApiModel("模版参数::小程序")
    public static class MiniProgram {
        private String appid;
        private String pagePath;
        private Boolean usePath = false;

        public WxMpTemplateMessage.MiniProgram asMiniProgram() {
            return new WxMpTemplateMessage.MiniProgram(appid, pagePath, usePath);
        }
    }
}
