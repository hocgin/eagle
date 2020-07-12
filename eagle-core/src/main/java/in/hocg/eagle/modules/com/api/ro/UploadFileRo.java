package in.hocg.eagle.modules.com.api.ro;

import in.hocg.eagle.basic.constant.datadict.FileRelType;
import in.hocg.eagle.basic.valid.EnumRange;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Created by hocgin on 2020/2/19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class UploadFileRo implements Serializable {

    @ApiModelProperty("引用类型(必须)")
    @EnumRange(enumClass = FileRelType.class)
    private Integer relType;
    @ApiModelProperty("引用ID(必须)")
    private Long relId;
    @ApiModelProperty("文件列表")
    private List<FileDto> files = Collections.emptyList();
    @ApiModelProperty("创建人ID(可选)")
    private Long creator;

    @Data
    @Accessors(chain = true)
    public static class FileDto implements Serializable {
        @ApiModelProperty("排序(可选)")
        private Integer sort;
        @ApiModelProperty("地址(必须)")
        private String url;
        @ApiModelProperty("文件名(可选)")
        private String filename;
    }
}
