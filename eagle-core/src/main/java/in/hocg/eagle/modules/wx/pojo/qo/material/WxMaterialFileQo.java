package in.hocg.eagle.modules.wx.pojo.qo.material;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by hocgin on 2020/5/6.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class WxMaterialFileQo {
    private MultipartFile file;
    private String filename;
    private String fileType;
}
