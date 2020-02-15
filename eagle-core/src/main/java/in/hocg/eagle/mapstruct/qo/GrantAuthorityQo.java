package in.hocg.eagle.mapstruct.qo;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.qo.IdQo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GrantAuthorityQo extends IdQo {
    @Length(min = 1, message = "请先选择权限")
    private List<Integer> authorities = Lists.newArrayList();
}
