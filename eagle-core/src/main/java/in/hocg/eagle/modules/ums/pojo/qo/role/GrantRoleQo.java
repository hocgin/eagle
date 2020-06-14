package in.hocg.eagle.modules.ums.pojo.qo.role;

import com.google.common.collect.Lists;
import in.hocg.web.pojo.qo.IdQo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by hocgin on 2020/2/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GrantRoleQo extends IdQo {
    @Size(min = 1, message = "请先选择角色")
    private List<Long> roles = Lists.newArrayList();
}
