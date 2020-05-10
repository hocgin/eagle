package in.hocg.eagle.utils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import in.hocg.eagle.basic.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * Created by hocgin on 2020/4/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class ExcelUtils {

    /**
     * 导入 Excel
     *
     * @param file
     * @param pojoClass
     * @param <T>
     * @return
     */
    public static  <T> List<T> importAsList(MultipartFile file, Class<T> pojoClass) {
        String filename = file.getOriginalFilename();
        if (Objects.isNull(filename)
            || (filename.lastIndexOf(".xlsx") == -1 && filename.lastIndexOf(".xls") == -1)) {
            throw ServiceException.wrap("文件格式错误");
        }
        ImportParams params = new ImportParams();
        try {
            return ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (Exception e) {
            log.error("导入失败", e);
            throw ServiceException.wrap("导入失败");
        }
    }

}
