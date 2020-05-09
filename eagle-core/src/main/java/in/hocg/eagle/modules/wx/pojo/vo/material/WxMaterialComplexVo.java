package in.hocg.eagle.modules.wx.pojo.vo.material;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.eagle.basic.aspect.named.InjectNamed;
import in.hocg.eagle.basic.aspect.named.Named;
import in.hocg.eagle.basic.aspect.named.NamedType;
import in.hocg.eagle.basic.jackson.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by hocgin on 2020/5/9.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class WxMaterialComplexVo {
    private Long id;
    @ApiModelProperty("APP ID")
    private String appid;
    @ApiModelProperty("素材类型, 0->图片; 1->语音; 2->视频; 3->缩略图; 4->图文")
    private Integer materialType;
    @ApiModelProperty("素材上传后获得")
    private Object materialResult;
    @ApiModelProperty("素材内容")
    private Object materialContent;

    private Long creator;
    @Named(idFor = "creator", type = NamedType.Nickname)
    private String creatorName;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    private Long lastUpdater;
    @ApiModelProperty("更新者")
    @Named(idFor = "lastUpdater", type = NamedType.Nickname)
    private String lastUpdaterName;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdatedAt;
}
