package in.hocg.eagle.mapstruct.vo.file;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by hocgin on 2020/2/19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class FileVo implements Serializable {
    @ApiModelProperty("地址")
    private String url;
    @ApiModelProperty("文件名")
    private String filename;
}
