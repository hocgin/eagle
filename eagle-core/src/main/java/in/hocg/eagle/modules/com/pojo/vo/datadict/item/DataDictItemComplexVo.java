package in.hocg.eagle.modules.com.pojo.vo.datadict.item;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.eagle.basic.aspect.named.InjectNamed;
import in.hocg.eagle.basic.aspect.named.Named;
import in.hocg.eagle.basic.aspect.named.NamedType;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.ext.jackson.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by hocgin on 2020/3/2.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class DataDictItemComplexVo {
    private Long id;
    private Long dictId;
    private String title;
    private String code;
    private String remark;
    private Integer sort;
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
