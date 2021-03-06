package in.hocg.eagle.utils;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * Created by hocgin on 2020/5/8.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class UrlUtils {

    public InputStream toInputStream(URI uri) throws IOException {
        return UrlUtils.toInputStream(uri.toURL());
    }

    public InputStream toInputStream(URL url) throws IOException {
        return url.openStream();
    }
}
