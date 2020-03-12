package in.hocg.eagle.modules.shop.pojo.qo;

import in.hocg.eagle.basic.constant.product.ProductStatus;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.basic.pojo.qo.Insert;
import in.hocg.eagle.basic.valid.RangeEnum;
import in.hocg.eagle.modules.base.pojo.qo.file.UploadFileDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class ProductSaveQo extends IdQo {
    @NotBlank(groups = {Insert.class}, message = "商品名称不能为空")
    @ApiModelProperty("商品名称")
    private String title;
    @ApiModelProperty("属性,JSON 格式")
    private String attrs;
    @NotNull(groups = {Insert.class}, message = "规格不能为空")
    @Size(min = 1, groups = {Insert.class}, message = "规格不能为空")
    @ApiModelProperty("规格列表")
    private List<String> spec;
    @NotNull(groups = {Insert.class}, message = "sku不能为空")
    @Size(min = 1, groups = {Insert.class}, message = "sku不能为空")
    @ApiModelProperty("SKU列表")
    private List<Sku> sku;
    @NotNull(groups = {Insert.class}, message = "商品状态错误")
    @RangeEnum(enumClass = ProductStatus.class,
        groups = {Insert.class}, message = "商品状态错误")
    private Integer status;
    @ApiModelProperty("采购地")
    private String procurement;
    @ApiModelProperty("图片")
    private List<UploadFileDto.FileDto> photos;
    @ApiModelProperty("主视频")
    private String videoUrl;
    @ApiModelProperty("分类ID")
    private Integer classifyId;

    @Data
    public static class Sku {
        @NotNull(groups = {Insert.class}, message = "规格不能为空")
        @Size(min = 1, groups = {Insert.class}, message = "规格不能为空")
        @ApiModelProperty("规格列表")
        private List<String> spec;
        @Min(value = 0L, groups = {Insert.class}, message = "库存数量错误")
        @ApiModelProperty("库存数量")
        private Integer inventory;
        @NotBlank(groups = {Insert.class}, message = "SKU编码不能为空")
        @ApiModelProperty("SKU编码")
        private String sku;
        @Min(value = 0L, groups = {Insert.class}, message = "价格错误")
        @ApiModelProperty("价格")
        private BigDecimal price;
        @ApiModelProperty("图片")
        private String imageUrl;
    }

}
