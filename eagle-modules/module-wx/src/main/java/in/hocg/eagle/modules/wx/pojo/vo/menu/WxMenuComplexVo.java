package in.hocg.eagle.modules.wx.pojo.vo.menu;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.web.aspect.named.InjectNamed;
import in.hocg.web.aspect.named.Named;
import in.hocg.web.aspect.named.NamedType;
import in.hocg.web.constant.datadict.Enabled;
import in.hocg.web.constant.datadict.WxMenuType;
import in.hocg.web.jackson.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Created by hocgin on 2020/5/4.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class WxMenuComplexVo {
    private Long id;
    private String title;
    @ApiModelProperty("APP ID")
    private String appid;
    @ApiModelProperty("上传个性化菜单后的 menu_id (仅菜单类型为: 个性化菜单)")
    private String menuId;
    @ApiModelProperty("菜单对象数组")
    private List<Button> button = Collections.emptyList();
    @ApiModelProperty("菜单匹配规则(仅菜单类型为: 个性化菜单)")
    private MatchRule matchRule;

    @ApiModelProperty("菜单类型, 0->通用菜单; 1->个性化菜单")
    private Integer menuType;
    @Named(idFor = "menuType", type = NamedType.DataDict, args = {WxMenuType.KEY})
    private String menuTypeName;

    @ApiModelProperty("启用状态[0:为禁用状态;1:为正常状态]")
    private Integer enabled;
    @Named(idFor = "enabled", type = NamedType.DataDict, args = {Enabled.KEY})
    private String enabledName;
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

    @Data
    public static class Button {
        private String type;
        private String name;
        private String key;
        private String url;
        private String mediaId;
        private String appid;
        private String pagepath;
        private List<Button.SubButton> subButtons = Collections.emptyList();

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
