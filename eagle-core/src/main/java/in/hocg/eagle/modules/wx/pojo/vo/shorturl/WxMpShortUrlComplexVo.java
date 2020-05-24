package in.hocg.eagle.modules.wx.pojo.vo.shorturl;

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
public class WxMpShortUrlComplexVo {
    private Long id;
    @ApiModelProperty("开发者ID(AppID)")
    private String appid;
    @ApiModelProperty("长链接")
    private String longUrl;
    @ApiModelProperty("短链接")
    private String shortUrl;
    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;
    @ApiModelProperty("创建者")
    private Long creator;
}
