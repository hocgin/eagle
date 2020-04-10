package in.hocg.eagle.utils.file;

import lombok.experimental.UtilityClass;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by hocgin on 2020/3/27.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class FileUtils {

    /**
     * 图片转成文件
     *
     * @param bi
     * @return
     */
    public static File toFile(BufferedImage bi) {
        Path file;
        try {
            file = Files.createTempFile("img", ".png");
            ImageIO.write(bi, "png", file.toFile());
            return file.toFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
