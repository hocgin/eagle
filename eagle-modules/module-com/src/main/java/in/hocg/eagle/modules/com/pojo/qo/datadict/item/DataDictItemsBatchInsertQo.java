package in.hocg.eagle.modules.com.pojo.qo.datadict.item;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hocgin on 2020/3/2.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class DataDictItemsBatchInsertQo {
    @NotNull
    private Long dictId;
    @Valid
    private List<DataDictItemInsertQo> items = new ArrayList<>();
}
