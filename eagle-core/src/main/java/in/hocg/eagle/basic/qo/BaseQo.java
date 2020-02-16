package in.hocg.eagle.basic.qo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.basic.result.ResultCode;
import in.hocg.eagle.basic.security.SecurityContext;
import in.hocg.eagle.basic.security.User;
import in.hocg.eagle.utils.DateUtils;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

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
    
    public <T> T getUserId() {
        final Optional<User> userOptional = getUser();
        if (userOptional.isPresent()) {
            return (T) userOptional.get().getId();
        } else {
            throw ServiceException.wrap(ResultCode.AUTHENTICATION_ERROR.getMessage());
        }
    }
    
    public Optional<User> getUser() {
        return SecurityContext.getCurrentUser();
    }
}
