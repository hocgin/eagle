package in.hocg.basic.api.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.web.aspect.named.InjectNamed;
import in.hocg.web.aspect.named.Named;
import in.hocg.web.aspect.named.NamedType;
import in.hocg.web.constant.datadict.ProductPublishStatus;
import in.hocg.web.jackson.LocalDateTimeSerializer;
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
    private Integer deleteStatus;
    @ApiModelProperty("单位")
    private String unit;
    @ApiModelProperty("商品重量(克)")
    private BigDecimal weight;
    @Named(idFor = "publishStatus", type = NamedType.DataDict, args = {ProductPublishStatus.KEY})
    private String publishStatusName;

    @ApiModelProperty("SKU")
    private List<SkuComplexVo> sku = Collections.emptyList();

    @ApiModelProperty("图片列表")
    private List<PhotoComplexVo> photos = Collections.emptyList();

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

    @Data
    public static class PhotoComplexVo {
        @ApiModelProperty("地址")
        private String url;
        @ApiModelProperty("文件名")
        private String filename;
    }
}
