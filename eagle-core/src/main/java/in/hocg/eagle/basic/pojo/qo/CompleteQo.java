package in.hocg.eagle.basic.pojo.qo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Created by hocgin on 2020/3/31.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class CompleteQo extends BaseQo {
    private int page = 1;
    private int size = 30;

    @JsonIgnore
    public Page page() {
        return new Page<>(this.page, this.size, false);
    }
}
