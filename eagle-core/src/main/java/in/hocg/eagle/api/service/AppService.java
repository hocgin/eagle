package in.hocg.eagle.api.service;

import in.hocg.eagle.api.pojo.SignUpQo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Created by hocgin on 2020/3/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AppService {

    public void signUp(SignUpQo qo) {

    }
}
