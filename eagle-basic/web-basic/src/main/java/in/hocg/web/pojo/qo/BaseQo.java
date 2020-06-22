package in.hocg.web.pojo.qo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import in.hocg.web.exception.ServiceException;
import in.hocg.web.result.ResultCode;
import in.hocg.web.security.SecurityContext;
import in.hocg.web.security.User;
import in.hocg.web.utils.DateUtils;
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