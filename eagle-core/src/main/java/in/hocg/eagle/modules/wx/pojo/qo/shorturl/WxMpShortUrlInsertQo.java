package in.hocg.eagle.modules.wx.pojo.qo.shorturl;

import in.hocg.web.pojo.qo.BaseQo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

/**
 * Created by hocgin on 2020/5/24.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WxMpShortUrlInsertQo extends BaseQo {
    @NotBlank(message = "APP ID不能为空")
    private String appid;
    @URL(message = "错误的长链接")
    @NotBlank(message = "长链接")
    private String longUrl;

}
