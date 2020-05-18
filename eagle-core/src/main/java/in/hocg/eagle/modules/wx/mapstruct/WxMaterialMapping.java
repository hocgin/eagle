package in.hocg.eagle.modules.wx.mapstruct;

import in.hocg.eagle.basic.constant.datadict.wx.WxMaterialType;
import in.hocg.eagle.modules.wx.entity.WxMaterial;
import in.hocg.eagle.modules.wx.pojo.qo.material.WxMaterialUploadNewsQo;
import in.hocg.eagle.modules.wx.pojo.vo.material.WxMaterialComplexVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/5/8.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface WxMaterialMapping {
    @Mapping(target = "thumbUrl", ignore = true)
    @Mapping(target = "thumbMediaId", ignore = true)
    WxMaterialType.News.NewsItem asWxMaterialType0News0NewsItem(WxMaterialUploadNewsQo.NewsItem item);

    @Mapping(target = "materialTypeName", ignore = true)
    @Mapping(target = "materialResult", ignore = true)
    @Mapping(target = "materialContent", ignore = true)
    @Mapping(target = "lastUpdaterName", ignore = true)
    @Mapping(target = "creatorName", ignore = true)
    WxMaterialComplexVo asWxMaterialComplex(WxMaterial entity);
}
