package in.hocg.web.manager;

import com.aliyun.oss.OSSClient;
import com.google.common.io.Files;
import in.hocg.web.utils.LangUtils;
import in.hocg.web.utils.file.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hocgin on 2020/3/27.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class OssManager {
    private final OSSClient ossClient;
    private final static String SPACE = "daigou-test";
    private final static String PREFIX_URL = "https://daigou-test.oss-cn-beijing.aliyuncs.com/";

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    public String uploadToOss(MultipartFile file) {
        try {
            final File uploadFile = FileUtils.createTempFile(file.getOriginalFilename()).toFile();
            file.transferTo(uploadFile);
            final String fileName = LangUtils.md5(Files.toByteArray(uploadFile)) + "/" + uploadFile.getName();
            return uploadToOss(uploadFile, fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    public String uploadToOss(File file, String filename) {
        return uploadToOss(file, filename, SPACE);
    }

    /**
     * 上传文件到OSS
     *
     * @param file
     * @param filename
     * @param space
     * @return
     */
    private String uploadToOss(File file, String filename, String space) {
        try {
            return this.uploadToOss(java.nio.file.Files.newInputStream(file.toPath()), filename, space);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String uploadToOss(InputStream is, String filename, String space) {
        ossClient.putObject(space, filename, is);
        return PREFIX_URL + filename;
    }
}
