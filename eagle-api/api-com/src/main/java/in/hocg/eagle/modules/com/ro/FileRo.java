package in.hocg.eagle.modules.com.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by hocgin on 2020/6/23.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class FileRo implements Serializable {
    @ApiModelProperty("排序(可选)")
    private Integer sort;
    @ApiModelProperty("地址(必须)")
    private String url;
    @ApiModelProperty("文件名(可选)")
    private String filename;
}
