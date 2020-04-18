package in.hocg.eagle.modules.com.pojo.vo.district;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by hocgin on 2020/4/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class DistrictComplexVo {
    private Long id;
    @ApiModelProperty("区域编码")
    private String adCode;
    @ApiModelProperty("城市编码")
    private String cityCode;
    @ApiModelProperty("名称")
    private String title;
}
