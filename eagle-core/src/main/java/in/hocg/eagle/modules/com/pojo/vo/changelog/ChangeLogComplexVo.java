package in.hocg.eagle.modules.com.pojo.vo.changelog;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import in.hocg.eagle.basic.aspect.named.InjectNamed;
import in.hocg.eagle.basic.aspect.named.Named;
import in.hocg.eagle.basic.aspect.named.NamedType;
import in.hocg.eagle.basic.jackson.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by hocgin on 2020/4/11.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
@InjectNamed
public class ChangeLogComplexVo {

    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("日志编号")
    private String logSn;
    @ApiModelProperty("业务类型: 如: 订单")
    private Integer refType;
    @ApiModelProperty("业务ID: 如: 订单ID")
    private Long refId;
    @ApiModelProperty("操作类型: 0->新增, 1->修改, 2->删除")
    private Integer changeType;
    @ApiModelProperty("创建时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    @ApiModelProperty("创建人")
    private Long creator;
    @Named(idFor = "creator", type = NamedType.Nickname)
    private String creatorName;

    private List<FieldChangeComplexVo> changes = Lists.newArrayList();
}
