package in.hocg.eagle.modules.wx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 微信用户表
 * </p>
 *
 * @author hocgin
 * @since 2020-04-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wx_user")
public class WxUser extends AbstractEntity<WxUser> {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("用户的标识，对当前公众号唯一")
    @TableField(value = "openid")
    private String openid;
    @TableField("appid")
    private String appid;
    @TableField("account_id")
    private Long accountId;
    @ApiModelProperty("用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。")
    @TableField("subscribe")
    private Boolean subscribe;
    @ApiModelProperty("用户的昵称")
    @TableField("nickname")
    private String nickname;
    @ApiModelProperty("用户的性别，值为1时是男性，值为2时是女性，值为0时是未知")
    @TableField("sex")
    private Integer sex;
    @ApiModelProperty("用户所在城市")
    @TableField("city")
    private String city;
    @ApiModelProperty("用户所在国家")
    @TableField("country")
    private String country;
    @ApiModelProperty("用户所在省份")
    @TableField("province")
    private String province;
    @ApiModelProperty("用户的语言，简体中文为zh_CN")
    @TableField("language")
    private String language;
    @ApiModelProperty("用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。")
    @TableField("headimgurl")
    private String headimgurl;
    @ApiModelProperty("用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间")
    @TableField("subscribe_time")
    private LocalDateTime subscribeTime;
    @ApiModelProperty("只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。")
    @TableField("unionid")
    private String unionid;
    @ApiModelProperty("公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注")
    @TableField("remark")
    private String remark;
    @ApiModelProperty("返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENE_PROFILE_LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他")
    @TableField("subscribe_scene")
    private String subscribeScene;
    @ApiModelProperty("二维码扫码场景（开发者自定义）")
    @TableField("qr_scene")
    private String qrScene;
    @ApiModelProperty("二维码扫码场景（开发者自定义）")
    @TableField("qr_scene_str")
    private String qrSceneStr;
    @TableField("created_at")
    private LocalDateTime createdAt;


    @Override
    public Serializable pkVal() {
        return this.openid;
    }

}
