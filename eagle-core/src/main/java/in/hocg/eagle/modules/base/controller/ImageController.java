package in.hocg.eagle.modules.base.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by hocgin on 2020/2/17.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/image")
public class ImageController {
    
    @PostMapping("/upload")
    public void uploadImage(@RequestPart("image") MultipartFile image) {
        throw new UnsupportedOperationException("还没有实现");
    }
}
