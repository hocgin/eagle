package in.hocg.eagle.basic.message.core.transactional;

import in.hocg.eagle.utils.string.JsonUtils;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

/**
 * Created by hocgin on 2020/7/20.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class TransactionalMessage {
    private String groupSn;
    private Map<String, Object> headers = Collections.emptyMap();
    private String destination;
    private String payload;
    private LocalDateTime preparedAt;

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public void setPayload(Object payload) {
        this.payload = JsonUtils.toJSONString(payload);
    }

    public TransactionalMessage() {
        this.preparedAt = LocalDateTime.now();
    }

}
