package in.hocg.eagle.modules.wx.pojo.qo.user;

import in.hocg.web.pojo.qo.BaseQo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by hocgin on 2020/5/10.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WxMpUserSearchQo extends BaseQo {
    private String appid;
    @ApiModelProperty("商品名称/商品ID")
    private String keyword;
}
