package in.hocg.eagle.modules.base.controller;


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
    
    @PostMapping("/upload")
    public Result<String> upload(@RequestPart("file") MultipartFile file) {
        return Result.success("http://www.example/file-url/还没有实现");
    }
}

