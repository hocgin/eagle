package in.hocg.eagle.basic.lang;

import com.talanlabs.avatargenerator.Avatar;
import com.talanlabs.avatargenerator.GitHubAvatar;
import com.talanlabs.avatargenerator.utils.AvatarUtils;
import lombok.experimental.UtilityClass;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by hocgin on 2020/3/27.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class Avatars {
    public static final Avatar AVATAR = GitHubAvatar.newAvatarBuilder()
        .build();

    /**
     * 获取头像
     *
     * @param code
     * @return
     */
    public static BufferedImage getAvatar(long code) {
        return createAvatar(Avatars.AVATAR, code);
    }

    /**
     * 创建头像
     *
     * @param avatar
     * @param code
     * @return
     */
    private static BufferedImage createAvatar(Avatar avatar, long code) {
        int size = avatar.getWidth();
        BufferedImage dest = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = dest.createGraphics();
        AvatarUtils.activeAntialiasing(g2);
        g2.drawImage(avatar.create(code), 0, 0, size, size, null);
        g2.dispose();
        return dest;
    }

}
