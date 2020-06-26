package in.hocg.eagle.basic.ext.mybatis.basic;


import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by hocgin on 2020/2/11.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface AbstractService<T> extends IService<T> {

    /**
     * 校验数据的约束
     *
     * @param entity
     */
    void validEntity(T entity);

    /**
     * 校验后，更新数据
     *
     * @param entity
     * @return
     */
    boolean validUpdateById(T entity);

    /**
     * 校验后，新增数据
     *
     * @param entity
     * @return
     */
    boolean validInsert(T entity);

    /**
     * 校验后，新增或更新数据
     *
     * @param entity
     */
    void validInsertOrUpdate(T entity);

    /**
     * 分片查询
     *
     * @param id
     * @return
     */
    List<T> selectListById(Collection<? extends Serializable> id);
}
