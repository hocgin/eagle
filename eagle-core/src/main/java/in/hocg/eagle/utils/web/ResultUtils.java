package in.hocg.eagle.utils.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.basic.pojo.ro.PageRo;

import java.util.Collections;
import java.util.List;

/**
 * Created by hocgin on 2020/3/6.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class ResultUtils {

    /**
     * 创建空分页对象
     *
     * @param pageQo 分页请求
     * @return 分页对象
     */
    public static <T> IPage<T> emptyPage(PageRo pageQo) {
        return emptyPage(pageQo.getPage(), pageQo.getSize());
    }

    /**
     * 空分页
     *
     * @param page 分页对象
     * @param <T>  对象
     * @return 新分页对象
     */
    public static <T> IPage<T> emptyPage(IPage<T> page) {
        return page(page.getTotal(), page.getCurrent(), page.getSize(), Collections.emptyList());
    }

    /**
     * 创建空分页对象
     *
     * @param current 当前页数
     * @param size    当前页数量
     * @return 分页对象
     */
    public static <T> IPage<T> emptyPage(long current, long size) {
        return page(0L, current, size, Collections.emptyList());
    }

    /**
     * 创建分页对象
     *
     * @param total   总数量
     * @param current 当前页
     * @param size    每页数量
     * @param data    当前页数据
     * @param <T>     对象
     * @return 分页对象
     */
    public static <T> IPage<T> page(long total, long current, long size, List<T> data) {
        return new Page<T>(current, size, total)
            .setRecords(data);
    }
}
