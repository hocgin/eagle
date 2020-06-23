package in.hocg.eagle.modules.ums.pojo.vo.authority;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import in.hocg.eagle.modules.ums.entity.Role;
import in.hocg.web.aspect.named.InjectNamed;
import in.hocg.web.aspect.named.Named;
import in.hocg.web.aspect.named.NamedType;
import in.hocg.web.constant.datadict.AuthorityType;
import in.hocg.web.constant.datadict.Enabled;
import in.hocg.web.constant.datadict.Platform;
import in.hocg.web.jackson.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by hocgin on 2020/2/25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class AuthorityComplexAndRoleVo implements Serializable {
    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("权限类型")
    private Integer type;
    @Named(idFor = "type", type = NamedType.DataDict, args = {AuthorityType.KEY})
    private String typeName;
    @ApiModelProperty("权限码")
    private String authorityCode;
    @ApiModelProperty("父级")
    private Long parentId;
    @Named(idFor = "parentId", type = NamedType.AuthorityTitle)
    private String parentName;
    @ApiModelProperty("开启状态")
    private Integer enabled;
    @Named(idFor = "enabled", type = NamedType.DataDict, args = {Enabled.KEY})
    private String enabledName;
    @ApiModelProperty("平台")
    private Integer platform;
    @Named(idFor = "platform", type = NamedType.DataDict, args = {Platform.KEY})
    private String platformName;
    @ApiModelProperty("权重")
    private Long sort;
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
    @ApiModelProperty("角色列表")
    private List<Role> roles = Lists.newArrayList();
}
