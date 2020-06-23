package in.hocg.eagle.modules.mms.pojo.vo.notify;

import in.hocg.basic.api.vo.NotifyComplexVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;

/**
 * Created by hocgin on 2020/3/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class SummaryVo {
    @ApiModelProperty("已读数量")
    private Integer ready;
    @ApiModelProperty("未读数量")
    private Integer unready;

    @ApiModelProperty("5条私信")
    private List<NotifyComplexVo> privateLetter = Collections.emptyList();

    @ApiModelProperty("5条订阅")
    private List<NotifyComplexVo> subscription = Collections.emptyList();

    @ApiModelProperty("5条公告")
    private List<NotifyComplexVo> announcement = Collections.emptyList();

}
