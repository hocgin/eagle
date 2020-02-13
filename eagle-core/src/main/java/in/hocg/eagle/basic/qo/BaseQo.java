package in.hocg.eagle.basic.qo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.hocg.eagle.utils.DateUtils;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by hocgin on 2020/1/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public abstract class BaseQo implements Serializable {
    
    /**
     * 创建时间
     */
    @JsonIgnore
    @Getter
    private LocalDateTime createdAt = LocalDateTime.now();
    
    public Date getCreatedAtAsDate() {
        return DateUtils.getDate(createdAt);
    }
}
