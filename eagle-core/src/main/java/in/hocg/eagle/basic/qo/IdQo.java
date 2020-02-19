package in.hocg.eagle.basic.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class IdQo extends BaseQo {
    @Getter
    @Setter
    @ApiModelProperty("Id")
    private Long id;
}
