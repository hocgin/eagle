package in.hocg.eagle.modules.ums.pojo.qo.account.group;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.pojo.ro.BaseRo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by hocgin on 2020/4/19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AccountGroupMemberDeleteQo extends BaseRo {
    private Long groupId;
    private List<Long> members = Lists.newArrayList();
}
