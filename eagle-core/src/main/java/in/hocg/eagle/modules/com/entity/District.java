package in.hocg.eagle.modules.com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.ext.mybatis.tree.TreeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * [基础模块] 城市规划表
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("com_district")
public class District extends TreeEntity<District> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("区域编码")
    @TableField("ad_code")
    private String adCode;
    @ApiModelProperty("城市编码")
    @TableField("city_code")
    private String cityCode;
    @ApiModelProperty("城市规划级别")
    @TableField("level")
    private Integer level;
    @ApiModelProperty("中心(纬度)")
    @TableField("lat")
    private BigDecimal lat;
    @ApiModelProperty("中心(经度)")
    @TableField("lng")
    private BigDecimal lng;
    @ApiModelProperty("名称")
    @TableField("title")
    private String title;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
