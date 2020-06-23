package in.hocg.eagle.modules.crm.api;

import in.hocg.basic.api.vo.CommentComplexVo;

/**
 * Created by hocgin on 2020/6/23.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface CommentAPI {

    CommentComplexVo selectOne(Long id);
}
