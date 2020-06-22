package in.hocg.eagle.modules.pms.pojo.dto.spec;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/3/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class SpecValueDto {
    @NotNull
    private Long skuId;
    @NotNull
    private String value;
}
