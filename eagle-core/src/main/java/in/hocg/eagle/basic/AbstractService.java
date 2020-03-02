package in.hocg.eagle.basic;


import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Created by hocgin on 2020/2/11.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface AbstractService<T> extends IService<T> {
    void verifyEntity(T entity);
}
