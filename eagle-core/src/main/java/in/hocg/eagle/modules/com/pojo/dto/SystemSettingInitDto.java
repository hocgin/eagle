package in.hocg.eagle.modules.com.pojo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by hocgin on 2020/7/21.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class SystemSettingInitDto {
    private String defaultValue;
    private String title;
    private String code;
    private String remark;
    private Boolean deprecated;
}
