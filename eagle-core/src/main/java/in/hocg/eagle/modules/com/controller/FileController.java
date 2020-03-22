package in.hocg.eagle.modules.com.controller;


import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.result.Result;
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

    @UseLogger("上传文件")
    @PostMapping("/upload")
    public Result<String> upload(@RequestPart("file") MultipartFile file) {
        return Result.success("https://dss0.baidu.com/73F1bjeh1BF3odCf/it/u=1130389758,912252089&fm=85&s=2C14ED13D83357A11999E4D3030080A2");
    }
}

