package in.hocg.eagle.modules.test.service.impl;

import in.hocg.eagle.modules.test.entity.Example;
import in.hocg.eagle.modules.test.mapper.ExampleMapper;
import in.hocg.eagle.modules.test.service.ExampleService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class ExampleServiceImpl extends AbstractServiceImpl<ExampleMapper, Example> implements ExampleService {

}
