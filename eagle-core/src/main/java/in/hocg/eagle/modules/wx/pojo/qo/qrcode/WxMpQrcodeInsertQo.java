package in.hocg.eagle.modules.wx.pojo.qo.qrcode;

import in.hocg.eagle.basic.pojo.ro.BaseRo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/5/24.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WxMpQrcodeInsertQo extends BaseRo {
    @NotNull(message = "APP ID不能为空")
    private String appid;
    @NotNull
    @ApiModelProperty("使用过期二维码")
    private Boolean useExpireQrCode;
    @NotNull
    @ApiModelProperty("使用Int场景ID")
    private Boolean useSceneId;
    private Integer expireSeconds;
    private Integer sceneId;
    private String sceneStr;
}
