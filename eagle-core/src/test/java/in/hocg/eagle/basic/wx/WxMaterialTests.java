package in.hocg.eagle.basic.wx;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMaterialService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import org.junit.Test;

import java.io.File;

/**
 * Created by hocgin on 2020/4/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class WxMaterialTests extends WxTests {

    @Test
    public void testUploadFile() {
        final File file = new File("/Users/hocgin/Pictures/hocgin/hocgin.png");
        final WxMpMaterialUploadResult result = wxMpManager.materialImageUpload(appid, file);
        log.info("上传结果: {}", result);
    }

    @Test
    public void testUploadNews() throws WxErrorException {
        final WxMpService wxMpService = wxMpManager.getWxMpService();
        final WxMpMaterialService materialService = wxMpService.getMaterialService();
        WxMpMaterialNews material = new WxMpMaterialNews();
        final WxMpMaterialNews.WxMpMaterialNewsArticle article = new WxMpMaterialNews.WxMpMaterialNewsArticle();

        // 必须
        article.setTitle("hocgin 标题");
        article.setThumbMediaId("GU9CG0HBBSEMeYwE_AHCVWPL2QU0LNM6earB2U_A_VE");
        article.setContent("<h>内容</h>");

        material.addArticle(article);
        final WxMpMaterialUploadResult result = materialService.materialNewsUpload(material);
        log.info("上传结果: {}", result);
    }
}
