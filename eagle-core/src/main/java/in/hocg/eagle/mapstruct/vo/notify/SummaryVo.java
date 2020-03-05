package in.hocg.eagle.mapstruct.vo.notify;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

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
    private List<NotifyItemVo> privateLetter = Lists.newArrayList();

    @ApiModelProperty("5条订阅")
    private List<NotifyItemVo> subscription = Lists.newArrayList();

    @ApiModelProperty("5条公告")
    private List<NotifyItemVo> announcement = Lists.newArrayList();

    enum SummaryType {

    }
}
