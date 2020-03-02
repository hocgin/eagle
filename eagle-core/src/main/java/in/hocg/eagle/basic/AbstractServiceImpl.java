package in.hocg.eagle.basic;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * Created by hocgin on 2020/1/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public abstract class AbstractServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T>
        implements AbstractService<T> {
    @Override
    public void verifyEntity(T entity) {
        throw new UnsupportedOperationException("未实现该函数");
    }
}
