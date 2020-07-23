package in.hocg.eagle.utils;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * Created by hocgin on 2020/2/11.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class DateUtils {
    public final DateTimeFormatter DATE_FORMAT_2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public final DateTimeFormatter SIMPLE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    public Date getDate(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            return null;
        }
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    public String format(LocalDateTime localDateTime, DateTimeFormatter formatter) {
        return localDateTime.format(formatter);
    }

    public LocalDateTime format(CharSequence dateTimeStr, DateTimeFormatter formatter) {
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    public LocalDateTime ofLong(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), getDefaultZoneId());
    }

    public ZoneId getDefaultZoneId() {
        return ZoneId.systemDefault();
    }

    public Long getTimestamp(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            return null;
        }
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }
}
