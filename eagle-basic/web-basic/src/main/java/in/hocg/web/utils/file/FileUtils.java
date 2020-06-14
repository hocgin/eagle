package in.hocg.web.utils.file;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
    public File toFile(BufferedImage bi) {
        Path file;
        try {
            file = Files.createTempFile("img", ".png");
            ImageIO.write(bi, "png", file.toFile());
            return file.toFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public File toFile(URL url) throws IOException {
        final String path = url.getPath();
        final String filename = path.substring(path.lastIndexOf("/") + 1);
        final File result = createTempFile(filename).toFile();
        org.apache.commons.io.FileUtils.copyURLToFile(url, result);
        return result;
    }

    public Path createTempFile(@NonNull String filename) throws IOException {
        return Files.createTempDirectory("temp_file")
            .resolve(filename);
    }

}
