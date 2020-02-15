package in.hocg.eagle.mapstruct.qo;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.qo.IdQo;
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
    private List<Integer> roles = Lists.newArrayList();
}
