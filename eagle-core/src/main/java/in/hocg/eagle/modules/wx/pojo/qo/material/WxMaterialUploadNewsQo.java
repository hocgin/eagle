package in.hocg.eagle.modules.wx.pojo.qo.material;

import in.hocg.eagle.basic.pojo.qo.BaseQo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by hocgin on 2020/5/8.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class WxMaterialUploadNewsQo extends BaseQo {
    @NotNull(message = "APP ID不能为空")
    private String appid;
    @Size(min = 1, message = "文章")
    private List<NewsItem> newsItems;

    @Data
    public static class NewsItem {
        private String title;
        private String thumbUrl;
        private String author;
        private String digest;
        private Integer showCoverPic;
        private String content;
        private String contentSourceUrl;
        private Integer needOpenComment;
        private Integer onlyFansCanComment;
    }

}
