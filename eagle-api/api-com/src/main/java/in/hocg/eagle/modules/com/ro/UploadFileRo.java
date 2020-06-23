package in.hocg.eagle.modules.com.ro;

import in.hocg.web.constant.GlobalConstant;
import in.hocg.web.constant.datadict.FileRelType;
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
    private FileRelType relType;
    @ApiModelProperty("引用ID(必须)")
    private Long relId;
    @ApiModelProperty("文件列表")
    private List<FileRo> files = Collections.emptyList();
    @ApiModelProperty("创建人ID(可选)")
    private Long creator = GlobalConstant.SUPPER_ADMIN_USER_ID;

}
