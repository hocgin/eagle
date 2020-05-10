package in.hocg.eagle.modules.ums.pojo.vo.account.group;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.eagle.basic.aspect.named.InjectNamed;
import in.hocg.eagle.basic.aspect.named.Named;
import in.hocg.eagle.basic.aspect.named.NamedType;
import in.hocg.eagle.basic.constant.datadict.AccountGroupType;
import in.hocg.eagle.basic.constant.datadict.GroupMemberSource;
import in.hocg.eagle.basic.jackson.LocalDateTimeSerializer;
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
public class AccountGroupComplexVo {
    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("组名")
    private String title;
    @ApiModelProperty("组描述")
    private String remark;

    @ApiModelProperty("分组类型, 如: 通用")
    private Integer groupType;
    @Named(idFor = "groupType", type = NamedType.DataDict, args = {AccountGroupType.KEY})
    private String groupTypeName;
    @ApiModelProperty("成员来源: 0->所有, 1->自定义组员列表")
    private Integer memberSource;
    @Named(idFor = "memberSource", type = NamedType.DataDict, args = {GroupMemberSource.KEY})
    private String memberSourceName;

    @ApiModelProperty("创建时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    @ApiModelProperty("创建者")
    private Long creator;
    @Named(idFor = "creator", type = NamedType.Nickname)
    private String creatorName;
    @ApiModelProperty("更新时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdatedAt;
    @ApiModelProperty("更新者")
    private Long lastUpdater;
    @Named(idFor = "lastUpdater", type = NamedType.Nickname)
    private String lastUpdaterName;
}
