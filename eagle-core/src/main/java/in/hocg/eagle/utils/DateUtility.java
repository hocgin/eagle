package in.hocg.eagle.utils;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * Created by hocgin on 2020/2/11.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class DateUtility {
    
    public static Date getDate(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            return null;
        }
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }
}
