package in.hocg.eagle.basic.qo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by hocgin on 2020/1/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageQo extends BaseQo {
    private int limit = 10;
    private int page = 1;
}
