package in.hocg.eagle;

import in.hocg.eagle.basic.constant.message.TopicConstant;
import in.hocg.eagle.basic.message.MessageFactory;
import in.hocg.eagle.basic.message.core.transactional.TransactionalMessage;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.com.entity.PersistenceMessage;
import in.hocg.eagle.modules.mms.message.body.TestBody2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hocgin on 2020/2/11.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EnableCaching
@EnableAsync
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
@RestController
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @GetMapping("/worked")
    public Result worked() {
        return Result.success();
    }
}
