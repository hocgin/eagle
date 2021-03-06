package in.hocg.eagle.basic.pojo.ro;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class PageRo extends BaseRo {
    private int page = 1;
    private int size = 10;

    @JsonIgnore
    public Page page() {
        return new Page<>(page, size);
    }
}
