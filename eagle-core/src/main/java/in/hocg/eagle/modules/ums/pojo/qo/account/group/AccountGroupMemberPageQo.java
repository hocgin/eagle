package in.hocg.eagle.modules.ums.pojo.qo.account.group;

import in.hocg.eagle.basic.pojo.qo.PageQo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by hocgin on 2020/4/19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AccountGroupMemberPageQo extends PageQo {
    private Long groupId;
}
