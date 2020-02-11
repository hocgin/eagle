package in.hocg.eagle.basic;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

/**
 * Created by hocgin on 2020/1/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public abstract class AbstractServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T>
        implements AbstractService<T> {
    
}
