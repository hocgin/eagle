package in.hocg.eagle.modules.account.pojo.vo.role;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import in.hocg.eagle.basic.aspect.named.InjectNamed;
import in.hocg.eagle.basic.aspect.named.Named;
import in.hocg.eagle.basic.aspect.named.NamedType;
import in.hocg.eagle.basic.jackson.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by hocgin on 2020/2/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class RoleComplexAndAuthorityVo implements Serializable {
    @ApiModelProperty("ID")
    private Integer id;
    @ApiModelProperty("角色名称")
    private String title;
    @ApiModelProperty("角色授权码")
    private String roleCode;
    @ApiModelProperty("角色描述")
    private String remark;
    @ApiModelProperty("启用状态[0:为禁用状态;1:为正常状态]")
    private Integer enabled;
    @Named(idFor = "enabled", type = NamedType.DataDict)
    private String enabledName;
    @ApiModelProperty("平台")
    private Integer platform;
    @Named(idFor = "platform", type = NamedType.DataDict)
    private String platformName;
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
    @ApiModelProperty("权限列表")
    private List<AuthorityVo> authorities = Lists.newArrayList();

    @Data
    public static class AuthorityVo implements Serializable {
        @ApiModelProperty("ID")
        private Integer id;
        @ApiModelProperty("权限名称")
        private String title;
        @ApiModelProperty("权限类型")
        private Integer type;
        @ApiModelProperty("权限授权码")
        private String authorityCode;
    }
}
