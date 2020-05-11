package in.hocg.eagle.modules.wx.pojo.qo.menu;

import in.hocg.eagle.basic.pojo.qo.BaseQo;
import in.hocg.eagle.basic.pojo.qo.Insert;
import in.hocg.eagle.basic.pojo.qo.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
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
public class WxMenuUpdateQo extends BaseQo {
    private Long id;
    private String title;
    @Valid
    @NotNull(groups = {Insert.class, Update.class}, message = "一级菜单不能为空")
    @Size(groups = {Insert.class, Update.class}, min = 1, max = 3, message = "一级菜单数组，个数应为1~3个")
    @ApiModelProperty("菜单按钮")
    private List<Button> button = Collections.emptyList();
    @ApiModelProperty("菜单匹配规则")
    private WxMenuUpdateQo.MatchRule matchRule;

    @Data
    public static class Button {
        private String type;
        private String name;
        private String key;
        private String url;
        private String mediaId;
        private String appid;
        private String pagepath;
        @Size(groups = {Insert.class, Update.class}, min = 1, max = 5, message = "一级菜单数组，个数应为1~5个")
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
