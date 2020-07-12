package in.hocg.eagle.modules.pms.api.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.eagle.basic.aspect.named.InjectNamed;
import in.hocg.eagle.basic.aspect.named.Named;
import in.hocg.eagle.basic.aspect.named.NamedType;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.ext.jackson.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Created by hocgin on 2020/3/29.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class ProductCategoryComplexVo {
    @ApiModelProperty
    private Long id;
    @ApiModelProperty
    private Long parentId;
    @ApiModelProperty
    private String title;
    @ApiModelProperty
    private String remark;
    @ApiModelProperty
    private String imageUrl;
    @ApiModelProperty
    private List<String> keywords = Collections.emptyList();
    @ApiModelProperty("开启状态")
    private Integer enabled;
    @Named(idFor = "enabled", type = NamedType.DataDict, args = {Enabled.KEY})
    private String enabledName;

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
