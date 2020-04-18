package in.hocg.eagle.manager.dto;

import in.hocg.eagle.modules.com.pojo.qo.district.AMapDistrictDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by hocgin on 2020/4/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class AMapDistrictResultDto {
    @ApiModelProperty("状态")
    private Integer status;
    @ApiModelProperty("节点")
    private List<AMapDistrictDto> districts;

    public boolean isOk() {
        return Integer.valueOf(1).equals(status);
    }
}
