package in.hocg.eagle.modules.com.pojo.vo.systemsettings;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.eagle.basic.aspect.named.InjectNamed;
import in.hocg.eagle.basic.aspect.named.Named;
import in.hocg.eagle.basic.aspect.named.NamedType;
import in.hocg.eagle.basic.ext.jackson.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by hocgin on 2020/4/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class SystemSettingsComplexVo {
    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("配置名称")
    private String title;
    @ApiModelProperty("配置备注")
    private String remark;
    @ApiModelProperty("配置码")
    private String configCode;
    @ApiModelProperty("配置值")
    private String value;

    @ApiModelProperty("创建时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    @ApiModelProperty("创建者")
    private Long creator;
    @ApiModelProperty("创建人")
    @Named(idFor = "creator", type = NamedType.Nickname)
    private String creatorName;

    @ApiModelProperty("更新时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdatedAt;
    @ApiModelProperty("更新者")
    private Long lastUpdater;
    @ApiModelProperty("更新者")
    @Named(idFor = "lastUpdater", type = NamedType.Nickname)
    private String lastUpdaterName;
}
