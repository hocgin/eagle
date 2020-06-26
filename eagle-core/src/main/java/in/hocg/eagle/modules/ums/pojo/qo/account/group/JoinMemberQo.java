package in.hocg.eagle.modules.ums.pojo.qo.account.group;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.pojo.ro.BaseRo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by hocgin on 2020/4/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class JoinMemberQo extends BaseRo {
    @ApiModelProperty("ID")
    private Long groupId;
    @ApiModelProperty("群成员")
    private List<Long> members = Lists.newArrayList();
}
