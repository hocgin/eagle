package in.hocg.eagle.modules.wx.pojo.vo.message.template;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.basic.named.InjectNamed;
import in.hocg.basic.named.Named;
import in.hocg.basic.named.NamedType;
import in.hocg.web.jackson.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by hocgin on 2020/5/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class WxMpMessageTemplateComplexVo {
    private Long id;
    @ApiModelProperty("开发者ID(AppID)")
    private String appid;
    @ApiModelProperty("模版ID")
    private String templateId;
    @ApiModelProperty("模版标题")
    private String title;
    @ApiModelProperty("模板所属行业的一级行业")
    private String primaryIndustry;
    @ApiModelProperty("模板所属行业的二级行业")
    private String deputyIndustry;
    @ApiModelProperty("模板内容")
    private String content;
    @ApiModelProperty("模板示例")
    private String example;

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
