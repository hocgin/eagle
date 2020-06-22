package in.hocg.eagle.modules.wx.pojo.vo.user.tags;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by hocgin on 2020/5/19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class WxMpUserTagsComplexVo {
    private Long id;
    @ApiModelProperty("开发者ID(AppID)")
    private String appid;
    @ApiModelProperty("标签ID(来着微信)")
    private Long tagId;
    @ApiModelProperty("标签名称(来着微信)")
    private String name;
}
