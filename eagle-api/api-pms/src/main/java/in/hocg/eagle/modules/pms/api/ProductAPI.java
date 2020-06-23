package in.hocg.eagle.modules.pms.api;


import in.hocg.basic.api.vo.ProductComplexVo;

/**
 * Created by hocgin on 2020/6/23.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface ProductAPI {
    ProductComplexVo selectOne(Long id);

    ProductComplexVo selectOneByIdAndNotDeleted(Long id);
}
