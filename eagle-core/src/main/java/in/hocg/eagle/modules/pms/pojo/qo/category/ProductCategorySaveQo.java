package in.hocg.eagle.modules.pms.pojo.qo.category;

import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.basic.pojo.ro.Insert;
import in.hocg.eagle.basic.valid.EnumRange;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by hocgin on 2020/3/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductCategorySaveQo extends IdRo {
    @ApiModelProperty("父级")
    private Long parentId;
    @NotNull(groups = {Insert.class})
    @ApiModelProperty("商品品类名称")
    private String title;
    @ApiModelProperty("商品品类描述")
    private String remark;
    @ApiModelProperty("商品品类图片")
    private String imageUrl;
    @ApiModelProperty("商品品类关键词")
    private List<String> keywords;
    @ApiModelProperty("启用状态")
    @EnumRange(enumClass = Enabled.class)
    private Integer enabled;
    @ApiModelProperty("排序")
    private Integer sort;
}
