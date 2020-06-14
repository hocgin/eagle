package in.hocg.eagle.modules.com.controller;


import in.hocg.web.aspect.logger.UseLogger;
import in.hocg.web.result.Result;
import in.hocg.web.manager.OssManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 文件引用表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-02-19
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/file")
public class FileController {
    private final OssManager ossManager;

    @UseLogger("上传文件")
    @PostMapping("/upload")
    public Result<String> upload(@RequestPart("file") MultipartFile file) {
        return Result.success(ossManager.uploadToOss(file));
    }
}

