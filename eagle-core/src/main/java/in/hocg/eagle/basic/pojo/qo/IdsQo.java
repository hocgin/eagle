package in.hocg.eagle.basic.pojo.qo;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by hocgin on 2020/3/1.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class IdsQo extends BaseQo {
    @Getter
    @Setter
    @Size(min = 1, message = "参数错误")
    private List<Long> id = Lists.newArrayList();
}
