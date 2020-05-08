package in.hocg.eagle.basic.wx;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMaterialService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
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
        final WxMpMaterialUploadResult result = wxMpManager.uploadMaterialFile(appid, WxConsts.MediaFileType.IMAGE, file);
        log.info("上传结果: {}", result);
    }

    @Test
    public void testUploadAudioFile() throws WxErrorException {
        final File file = new File("/Users/hocgin/Downloads/Day5-实用价值.mp3");
        final WxMpService wxMpService = wxMpManager.getWxMpService();
        wxMpService.switchover(appid);
        final WxMpMaterialService materialService = wxMpService.getMaterialService();

        final WxMpMaterial material = new WxMpMaterial();
        material.setFile(file);
        material.setName(file.getName());
        material.setVideoIntroduction("svi");
        material.setVideoTitle("svt");
        final WxMpMaterialUploadResult result = materialService.materialFileUpload(WxConsts.MediaFileType.VOICE, material);
        log.info("上传结果: {}", result);
    }


    @Test
    public void testUploadFile2() {
        final WxMpService wxMpService = wxMpManager.getWxMpService();
        final WxMpMaterialService materialService = wxMpService.getMaterialService();
//        materialService.mediaImgUpload()
//        materialService.mediaUpload()
//        materialService.materialFileUpload()
//        materialService.materialNewsUpload()
    }

    @Test
    public void testUploadNews() throws WxErrorException {
        final WxMpService wxMpService = wxMpManager.getWxMpService();
        wxMpService.switchover(appid);
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
