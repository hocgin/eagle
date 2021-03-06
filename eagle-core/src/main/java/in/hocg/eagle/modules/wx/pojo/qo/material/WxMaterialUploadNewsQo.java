package in.hocg.eagle.modules.wx.pojo.qo.material;

import in.hocg.eagle.basic.constant.PatternConstant;
import in.hocg.eagle.basic.pojo.ro.BaseRo;
import in.hocg.eagle.utils.ValidUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by hocgin on 2020/5/8.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxMaterialUploadNewsQo extends BaseRo {
    @NotNull(message = "APP ID不能为空")
    private String appid;
    @Size(min = 1, message = "文章")
    private List<NewsItem> newsItems;

    @Data
    public static class NewsItem {
        @NotBlank(message = "标题不能为空")
        private String title;
        @Pattern(regexp = PatternConstant.URL, message = "图片地址错误")
        private String originalUrl;
        private String thumbMediaId;
        private String author;
        private String digest;
        @NotNull(message = "是否显示封面不能为空")
        private Boolean showCoverPic;
        @NotBlank(message = "图文消息正文不能为空")
        private String content;
        @NotBlank(message = "原文地址不能为空")
        @Pattern(regexp = PatternConstant.URL,message = "原文地址错误")
        private String contentSourceUrl;
        private Boolean needOpenComment;
        private Boolean onlyFansCanComment;
    }

    public void validThrow() {
        for (NewsItem item : newsItems) {
            ValidUtils.isFalse(StringUtils.isAllBlank(item.getOriginalUrl(), item.getThumbMediaId()), "请上传封面");
        }
    }

}
