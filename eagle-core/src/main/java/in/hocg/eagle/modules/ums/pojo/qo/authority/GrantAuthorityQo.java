package in.hocg.eagle.modules.ums.pojo.qo.authority;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GrantAuthorityQo extends IdRo {
    @Size(min = 1, message = "请先选择权限")
    private List<Long> authorities = Lists.newArrayList();
}
