package in.hocg.eagle.modules.wx.pojo.qo.material;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * Created by hocgin on 2020/5/6.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class WxMaterialInsertQo {

    private List<Articles> articles = Collections.emptyList();

    @Data
    @ApiModel("图文")
    public static class Articles {
        private String title;
        private String thumbMediaId;
        private String author;
        private String digest;
        private Integer showCoverPic;
        private String content;
        private String contentSourceUrl;
        private Integer needOpenComment;
        private Integer onlyFansCanComment;
    }
}
