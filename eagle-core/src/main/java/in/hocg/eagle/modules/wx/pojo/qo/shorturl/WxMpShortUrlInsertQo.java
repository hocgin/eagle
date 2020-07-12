package in.hocg.eagle.modules.wx.pojo.qo.shorturl;

import in.hocg.eagle.basic.constant.PatternConstant;
import in.hocg.eagle.basic.pojo.ro.BaseRo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Created by hocgin on 2020/5/24.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WxMpShortUrlInsertQo extends BaseRo {
    @NotBlank(message = "APP ID不能为空")
    private String appid;
    @Pattern(regexp = PatternConstant.URL,message = "错误的长链接")
    @NotBlank(message = "长链接")
    private String longUrl;

}
