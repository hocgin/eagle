package in.hocg.eagle.mapstruct.qo.datadict;

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
public class DataDictItemsPostQo {
    @NotNull
    private Long dictId;
    @Valid
    private List<DataDictItemPostQo> items = new ArrayList<>();
}
