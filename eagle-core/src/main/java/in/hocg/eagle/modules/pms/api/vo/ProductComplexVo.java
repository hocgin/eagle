package in.hocg.eagle.modules.pms.api.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.eagle.basic.aspect.named.InjectNamed;
import in.hocg.eagle.basic.aspect.named.Named;
import in.hocg.eagle.basic.aspect.named.NamedType;
import in.hocg.eagle.basic.constant.datadict.ProductPublishStatus;
import in.hocg.eagle.basic.ext.jackson.LocalDateTimeSerializer;
import in.hocg.eagle.modules.com.api.vo.FileVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
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
    @Named(idFor = "productCategoryId", type = NamedType.ProductCategoryName)
    private String productCategoryName;
    @ApiModelProperty("视频地址")
    private String videoUrl;
    @ApiModelProperty("产地")
    private String procurement;
    @ApiModelProperty("上架状态")
    private Integer publishStatus;
    @ApiModelProperty("删除状态")
    private Integer deleteStatus;
    @ApiModelProperty("单位")
    private String unit;
    @ApiModelProperty("商品重量(克)")
    private BigDecimal weight;
    @Named(idFor = "publishStatus", type = NamedType.DataDict, args = {ProductPublishStatus.KEY})
    private String publishStatusName;

    @ApiModelProperty("SKU")
    private List<SkuComplexVo> sku = Collections.emptyList();

    @ApiModelProperty("最低售卖金额")
    private BigDecimal minPrice;
    @ApiModelProperty("最高售卖金额")
    private BigDecimal maxPrice;

    @ApiModelProperty("图片列表")
    private List<FileVo> photos = Collections.emptyList();

    @ApiModelProperty("主图")
    private String mainPhotoUrl;

    @ApiModelProperty("创建时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    @ApiModelProperty("创建人")
    private Long creator;
    @Named(idFor = "creator", type = NamedType.Nickname)
    private String creatorName;
    @ApiModelProperty("最后更新时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdatedAt;
    @ApiModelProperty("最后更新时间")
    private Long lastUpdater;
    @Named(idFor = "lastUpdater", type = NamedType.Nickname)
    private String lastUpdaterName;
}
