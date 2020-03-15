package in.hocg.eagle.basic;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Created by hocgin on 2020/3/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SNCode {
    private final SnowFlake snowFlake;

    @Getter
    @RequiredArgsConstructor
    enum Type {
        Order("E1");
        private final String code;
    }

    /**
     * 订单编号
     * @return
     */
    public String getOrderSNCode() {
        return getSNCode(Type.Order);
    }

    private String getSNCode(Type type) {
        final long code = snowFlake.nextId();
        return type.getCode() + code;
    }

}
