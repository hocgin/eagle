package in.hocg.eagle.modules.com.pojo.qo.district;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.beust.jcommander.internal.Lists;
import lombok.Data;

import java.util.List;

/**
 * Created by hocgin on 2020/4/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class AMapExcelFileDto {
    @Excel(name = "中文名", isImportField = "true")
    private String name;
    @Excel(name = "adcode", isImportField = "true")
    private String adCode;
    @Excel(name = "citycode", isImportField = "true")
    private String cityCode;

    public List<String> getAdCodes() {
        return Lists.newArrayList(adCode.substring(0, 2), adCode.substring(2, 4), adCode.substring(4, 6));
    }

    public String getParentAdCode() {
        StringBuilder result = new StringBuilder();
        final List<String> adCodes = getAdCodes();
        boolean isSkip = false;
        final String ZERO = "00";
        for (int i = adCodes.size() - 1; i >= 0; i--) {
            final String code = adCodes.get(i);
            if (ZERO.equals(code)) {
                result.insert(0, ZERO);
            } else if (!isSkip) {
                isSkip = true;
                result.insert(0, ZERO);
            } else {
                result.insert(0, code);
            }
        }
        return result.toString();
    }
}
