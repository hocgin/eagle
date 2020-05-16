package in.hocg.eagle.basic.constant.datadict.wx;

import in.hocg.eagle.basic.constant.datadict.IntJSONEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by hocgin on 2020/5/6.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("微信素材类型")
@RequiredArgsConstructor
public enum WxMaterialType implements IntJSONEnum {
    Image(0, "图片", File.class, Result.class),
    Voice(1, "语音", File.class, Result.class),
    Video(2, "视频", Video.class, Result.class),
    Thumb(3, "缩略图", File.class, Result.class),
    News(4, "图文", News.class, Result.class);
    private final Integer code;
    private final String name;
    private final Class<?> content;
    private final Class<?> result;

    public static final String KEY = "wxMaterialType";

    public <T> T asResult(String json) {
        return (T) this.asClass(json, this.getResult());
    }

    public <T> T asContent(String json) {
        return (T) this.asClass(json, this.getContent());
    }

    /**
     * 转为媒体文件类型
     *
     * @return
     */
    public String asMediaFileType() {
        return name().toLowerCase();
    }

    @Data
    @Accessors(chain = true)
    @ApiModel("上传::通用文件")
    public static class File {
        private String url;
        private String filename;
    }

    @Data
    @ApiModel("上传::图文")
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class News extends ArrayList<WxMaterialType.News.NewsItem> {
        public News(@NotNull Collection<? extends NewsItem> c) {
            super(c);
        }

        @Data
        public static class NewsItem {
            private String title;
            private String thumbMediaId;
            private String thumbUrl;
            private String originalUrl;
            private String author;
            private String digest;
            private Boolean showCoverPic;
            private String content;
            private String contentSourceUrl;
            private Boolean needOpenComment;
            private Boolean onlyFansCanComment;
        }
    }

    @Data
    @ApiModel("上传::视频")
    @Accessors(chain = true)
    public static class Video {
        private String url;
        private String filename;
        private String title;
        private String introduction;
    }

    @Data
    @ApiModel("上传结果::通用")
    @Accessors(chain = true)
    public static class Result {
        private String mediaId;
        private String url;
    }

    @Data
    @ApiModel("上传结果::图片")
    @Accessors(chain = true)
    public static class ImageResult {
        private String url;
    }

}
