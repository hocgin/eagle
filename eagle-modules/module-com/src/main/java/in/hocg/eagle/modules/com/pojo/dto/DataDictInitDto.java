package in.hocg.eagle.modules.com.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;

/**
 * Created by hocgin on 2020/6/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class DataDictInitDto {
    @ApiModelProperty("字典名称")
    private String title;
    @ApiModelProperty("字典标识")
    private String code;
    @ApiModelProperty("启用状态")
    private Integer enabled;
    @ApiModelProperty("子项")
    private List<Item> children = Collections.emptyList();

    @Data
    @Accessors(chain = true)
    public static class Item {
        @ApiModelProperty("字典项名称")
        private String title;
        @ApiModelProperty("字典标识")
        private String code;
        @ApiModelProperty("启用状态")
        private Integer enabled;
    }
}
