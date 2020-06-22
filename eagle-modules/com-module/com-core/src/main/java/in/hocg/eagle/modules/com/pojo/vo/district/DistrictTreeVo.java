package in.hocg.eagle.modules.com.pojo.vo.district;

import in.hocg.web.datastruct.tree.AbstractTreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by hocgin on 2020/4/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DistrictTreeVo extends AbstractTreeNode<DistrictTreeVo> {
    private Long id;
    @ApiModelProperty("区域编码")
    private String adCode;
    @ApiModelProperty("城市编码")
    private String cityCode;
    @ApiModelProperty("名称")
    private String title;
}
