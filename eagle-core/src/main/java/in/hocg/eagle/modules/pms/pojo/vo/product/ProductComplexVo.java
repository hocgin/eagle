package in.hocg.eagle.modules.pms.pojo.vo.product;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.aspect.named.InjectNamed;
import in.hocg.eagle.basic.aspect.named.Named;
import in.hocg.eagle.basic.aspect.named.NamedType;
import in.hocg.eagle.modules.base.pojo.vo.file.FileVo;
import in.hocg.eagle.modules.pms.pojo.vo.sku.SkuComplexVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by hocgin on 2020/3/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class ProductComplexVo {
    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("属性")
    private String attrs;
    @ApiModelProperty("品类")
    private Long productCategoryId;
    @ApiModelProperty("视频地址")
    private String videoUrl;
    @ApiModelProperty("产地")
    private String procurement;
    @ApiModelProperty("上架状态")
    private Integer publishStatus;
    @Named(idFor = "publishStatus", type = NamedType.DataDict)
    private String publishStatusName;

    @ApiModelProperty("SKU")
    private List<SkuComplexVo> sku = Lists.newArrayList();

    @ApiModelProperty("图片列表")
    private List<FileVo> photos = Lists.newArrayList();
}
