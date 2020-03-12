package in.hocg.eagle.modules.shop.pojo.vo.product;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.aspect.named.InjectNamed;
import in.hocg.eagle.basic.aspect.named.Named;
import in.hocg.eagle.basic.aspect.named.NamedType;
import in.hocg.eagle.modules.base.pojo.vo.file.FileVo;
import in.hocg.eagle.modules.shop.pojo.vo.sku.SkuComplexVo;
import in.hocg.eagle.modules.shop.pojo.vo.spec.SpecComplexVo;
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
    @ApiModelProperty("分类")
    private Long classifyId;
    @ApiModelProperty("视频地址")
    private String videoUrl;
    @ApiModelProperty("产地")
    private String procurement;
    @ApiModelProperty("状态")
    private Integer status;
    @Named(idFor = "productStatus", type = NamedType.DataDict)
    private String statusName;

    @ApiModelProperty("规格")
    private List<SpecComplexVo> spec = Lists.newArrayList();
    @ApiModelProperty("SKU")
    private List<SkuComplexVo> sku = Lists.newArrayList();

    @ApiModelProperty("图片列表")
    private List<FileVo> photos = Lists.newArrayList();
}
