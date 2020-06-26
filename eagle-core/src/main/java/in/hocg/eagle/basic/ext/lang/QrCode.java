package in.hocg.eagle.basic.ext.lang;

import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.experimental.UtilityClass;

import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Created by hocgin on 2020/3/27.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class QrCode {
    private final Map<EncodeHintType, Object> DEFAULT_HINTS = Maps.newHashMap();

    static {
        // 字符编码
        DEFAULT_HINTS.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
        // 容错等级 L、M、Q、H 其中 L 为最低, H 为最高
        DEFAULT_HINTS.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 二维码与图片边距
        DEFAULT_HINTS.put(EncodeHintType.MARGIN, 2);
    }

    private BufferedImage getQrCode(String content, int width, int height) {
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, DEFAULT_HINTS);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }
}
