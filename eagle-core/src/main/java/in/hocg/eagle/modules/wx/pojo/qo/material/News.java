package in.hocg.eagle.modules.wx.pojo.qo.material;

import lombok.Data;

/**
 * Created by hocgin on 2020/5/6.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class News {
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
