package in.hocg.eagle.modules.wx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.web.AbstractService;
import in.hocg.web.pojo.qo.IdQo;
import in.hocg.eagle.modules.wx.entity.WxMaterial;
import in.hocg.eagle.modules.wx.pojo.qo.material.*;
import in.hocg.eagle.modules.wx.pojo.vo.material.WxMaterialComplexVo;

import java.io.InputStream;

/**
 * <p>
 * [微信模块] 微信素材库 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-05-05
 */
public interface WxMaterialService extends AbstractService<WxMaterial> {

    void uploadVoice(WxMaterialUploadVoiceQo qo);

    void uploadImage(WxMaterialUploadImageQo qo);

    void uploadVideo(WxMaterialUploadVideoQo qo);

    void uploadNews(WxMaterialUploadNewsQo qo);

    void updateNews(WxMaterialUpdateNewsQo qo);

    InputStream getVideoStream(String appid, String mediaId);

    InputStream getVoiceStream(String appid, String mediaId);

    InputStream getImageStream(String appid, String mediaId);

    IPage<WxMaterialComplexVo> paging(WxMaterialPageQo qo);

    WxMaterialComplexVo selectOne(IdQo qo);
}
