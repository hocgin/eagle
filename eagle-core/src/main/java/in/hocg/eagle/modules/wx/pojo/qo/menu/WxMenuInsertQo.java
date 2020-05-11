package in.hocg.eagle.modules.wx.pojo.qo.menu;

import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.constant.datadict.wx.WxMenuType;
import in.hocg.eagle.basic.pojo.qo.BaseQo;
import in.hocg.eagle.basic.valid.RangeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

/**
 * Created by hocgin on 2020/4/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxMenuInsertQo extends BaseQo {
    @NotBlank(message = "APP ID 不能为空")
    private String appid;
    @NotNull(message = "菜单类型不能为空")
    @RangeEnum(enumClass = WxMenuType.class, message = "菜单类型错误")
    private Integer menuType;
    @NotBlank(message = "标题不能为空")
    private String title;
    @Valid
    @NotNull(message = "一级菜单不能为空")
    @Size(min = 1, max = 3, message = "一级菜单数组，个数应为1~3个")
    @ApiModelProperty("菜单按钮")
    private List<Button> button = Collections.emptyList();
    @ApiModelProperty("菜单匹配规则")
    private WxMenuInsertQo.MatchRule matchRule;
    @ApiModelProperty("启用状态")
    @NotNull(message = "启用状态不能为空")
    @RangeEnum(enumClass = Enabled.class)
    private Integer enabled;

    @Data
    public static class Button {
        private String type;
        private String name;
        private String key;
        private String url;
        private String mediaId;
        private String appid;
        private String pagepath;
        @Size(max = 5, message = "一级菜单数组，个数应为1~5个")
        private List<SubButton> subButton = Collections.emptyList();

        @Data
        public static class SubButton {
            private String type;
            private String name;
            private String key;
            private String url;
            private String mediaId;
            private String appid;
            private String pagepath;
        }
    }

    @Data
    public static class MatchRule {
        private String tagId;
        private String sex;
        private String country;
        private String province;
        private String clientPlatformType;
        private String language;
    }
}
