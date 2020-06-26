package in.hocg.eagle.basic.pojo.ro;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.basic.result.ResultCode;
import in.hocg.eagle.basic.ext.security.SecurityContext;
import in.hocg.eagle.basic.ext.security.User;
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
public abstract class BaseRo implements Serializable {

    /**
     * 创建时间
     */
    @Getter
    @JsonIgnore
    @JSONField(serialize = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonIgnore
    @JSONField(serialize = false)
    public Date getCreatedAtAsDate() {
        return DateUtils.getDate(createdAt);
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public <T> T getUserId() {
        final Optional<User> userOptional = getUser();
        if (userOptional.isPresent()) {
            return (T) userOptional.get().getId();
        } else {
            throw ServiceException.wrap(ResultCode.AUTHENTICATION_ERROR.getMessage());
        }
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public Optional<User> getUser() {
        return SecurityContext.getCurrentUser();
    }
}
