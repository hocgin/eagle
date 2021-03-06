package in.hocg.eagle.modules.pms.pojo.qo;

import in.hocg.eagle.basic.constant.datadict.ProductPublishStatus;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.basic.pojo.ro.Insert;
import in.hocg.eagle.basic.pojo.ro.Update;
import in.hocg.eagle.basic.valid.EnumRange;
import in.hocg.eagle.modules.com.api.ro.UploadFileRo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hocgin on 2020/3/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductSaveQo extends IdRo {
    @NotBlank(groups = {Insert.class}, message = "商品名称不能为空")
    @ApiModelProperty("商品名称")
    private String title;
    @ApiModelProperty("属性,JSON 格式")
    private String attrs;
    @Valid
    @NotNull(groups = {Insert.class}, message = "sku不能为空")
    @Size(min = 1, groups = {Insert.class, Update.class}, message = "sku不能为空")
    @ApiModelProperty("SKU列表")
    private List<Sku> sku;
    @ApiModelProperty("采购地")
    private String procurement;
    @ApiModelProperty("图片")
    @NotNull(groups = {Insert.class}, message = "图片不能为空")
    @Size(min = 1, groups = {Insert.class, Update.class}, message = "图片不能为空")
    private List<UploadFileRo.FileDto> photos;
    @ApiModelProperty("主视频")
    private String videoUrl;
    @ApiModelProperty("品类ID")
    @NotNull(groups = {Insert.class}, message = "品类不能为空")
    private Long productCategoryId;
    @NotNull(groups = {Insert.class}, message = "商品状态错误")
    @EnumRange(enumClass = ProductPublishStatus.class,
        groups = {Insert.class}, message = "商品上架状态错误")
    @ApiModelProperty("上架状态: [0:下架, 1:上架]")
    private Integer publishStatus;
    @ApiModelProperty("单位")
    private String unit;
    @ApiModelProperty("商品重量(克)")
    private BigDecimal weight;
    @ApiModelProperty("删除状态: [0:未删除, 1:删除]")
    private Integer deleteStatus;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Sku extends IdRo {
        @NotNull(groups = {Insert.class, Update.class}, message = "规格不能为空")
        @Size(min = 1, groups = {Insert.class, Update.class}, message = "规格不能为空")
        @ApiModelProperty("规格列表")
        private List<Spec> spec;
        @Min(value = 0L, groups = {Insert.class, Update.class}, message = "库存数量错误")
        @ApiModelProperty("库存数量")
        private Integer stock;
        @NotBlank(groups = {Insert.class, Update.class}, message = "SKU编码不能为空")
        @ApiModelProperty("SKU编码")
        private String skuCode;
        @Min(value = 0L, groups = {Insert.class, Update.class}, message = "价格错误")
        @ApiModelProperty("价格")
        private BigDecimal price;
        @ApiModelProperty("图片")
        private String imageUrl;
    }

    @Data
    public static class Spec {
        @NotNull(groups = {Insert.class})
        @ApiModelProperty("规格属性")
        private String key;
        @NotNull(groups = {Insert.class})
        @ApiModelProperty("规格值")
        private String value;
    }
}
