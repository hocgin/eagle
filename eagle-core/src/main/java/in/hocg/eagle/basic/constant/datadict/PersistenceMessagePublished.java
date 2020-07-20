package in.hocg.eagle.basic.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/7/20.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("持久消息发布状态")
@RequiredArgsConstructor
public enum PersistenceMessagePublished implements DataDictEnum {
    Prepare(0, "准备状态"),
    Complete(1, "已完成状态");
    private final Integer code;
    private final String name;

    public static final String KEY = "PersistenceMessagePublished";
}
