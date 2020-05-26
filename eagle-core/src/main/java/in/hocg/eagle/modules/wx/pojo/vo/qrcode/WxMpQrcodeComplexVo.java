package in.hocg.eagle.modules.wx.pojo.vo.qrcode;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.eagle.basic.aspect.named.InjectNamed;
import in.hocg.eagle.basic.aspect.named.Named;
import in.hocg.eagle.basic.aspect.named.NamedType;
import in.hocg.eagle.basic.jackson.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by hocgin on 2020/5/24.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class WxMpQrcodeComplexVo {
    private Long id;
    @ApiModelProperty("开发者ID(AppID)")
    private String appid;
    @ApiModelProperty("过期时间(秒), -1 为永久型")
    private Integer expireSeconds;
    @ApiModelProperty("场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000")
    private Integer sceneId;
    @ApiModelProperty("场景值ID（字符串形式的ID），字符串类型，长度限制为1到64")
    private String sceneStr;
    @ApiModelProperty("获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码")
    private String ticket;
    @ApiModelProperty("二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片")
    private String url;
    private String qrcodeUrl;
    private Long creator;
    @Named(idFor = "creator", type = NamedType.Nickname)
    private String creatorName;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime expireAt;
}
