package in.hocg.eagle.mapstruct.qo;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.qo.BaseQo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GrantAuthorityQo extends BaseQo {
    private Serializable id;
    private List<Serializable> authorities = Lists.newArrayList();
}
