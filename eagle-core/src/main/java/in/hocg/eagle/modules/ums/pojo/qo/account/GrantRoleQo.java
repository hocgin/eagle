package in.hocg.eagle.modules.ums.pojo.qo.account;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by hocgin on 2020/2/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GrantRoleQo extends IdRo {
    @Size(min = 1, message = "请先选择角色")
    private List<Long> roles = Lists.newArrayList();
}
