package in.hocg.eagle.modules.ums.pojo.qo.account.group;

import in.hocg.eagle.basic.constant.datadict.AccountGroupType;
import in.hocg.eagle.basic.constant.datadict.GroupMemberSource;
import in.hocg.eagle.basic.pojo.qo.BaseQo;
import in.hocg.eagle.basic.pojo.qo.Insert;
import in.hocg.eagle.basic.pojo.qo.Update;
import in.hocg.eagle.basic.valid.EnumRange;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * Created by hocgin on 2020/4/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AccountGroupSaveQo extends BaseQo {

    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("组名")
    @NotBlank(groups = {Insert.class}, message = "组名不能为空")
    private String title;
    @ApiModelProperty("组描述")
    @NotBlank(groups = {Insert.class}, message = "描述不能为空")
    private String remark;
    @ApiModelProperty("分组类型, 如: 通用")
    @EnumRange(groups = {Insert.class, Update.class}, enumClass = AccountGroupType.class)
    private Integer groupType;
    @ApiModelProperty("成员来源: 0->所有, 1->自定义组员列表")
    @EnumRange(groups = {Insert.class, Update.class}, enumClass = GroupMemberSource.class)
    private Integer memberSource;

}
