package in.hocg.eagle.modules.ums.pojo.vo.role;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.web.aspect.named.InjectNamed;
import in.hocg.web.aspect.named.Named;
import in.hocg.web.aspect.named.NamedType;
import in.hocg.web.constant.datadict.Enabled;
import in.hocg.web.constant.datadict.Platform;
import in.hocg.web.jackson.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by hocgin on 2020/2/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class RoleComplexVo implements Serializable {
    private Integer id;
    @ApiModelProperty("角色名称")
    private String title;
    @ApiModelProperty("角色码")
    private String roleCode;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("启用状态")
    private Integer enabled;
    @Named(idFor = "enabled", type = NamedType.DataDict, args = {Enabled.KEY})
    private String enabledName;
    @ApiModelProperty("平台")
    private Integer platform;
    @Named(idFor = "platform", type = NamedType.DataDict, args = {Platform.KEY})
    private String platformName;
    @ApiModelProperty("创建时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    @ApiModelProperty("最后更新时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdatedAt;
}
