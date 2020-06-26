package in.hocg.eagle.basic.pojo.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class IdRo extends BaseRo {
    @Getter
    @Setter
    @ApiModelProperty("Id")
    private Long id;
}
