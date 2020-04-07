package in.hocg.eagle.manager;

import com.beust.jcommander.internal.Sets;
import com.google.common.collect.Lists;
import in.hocg.eagle.basic.ext.mail.Mail;
import in.hocg.eagle.basic.ext.mail.MailTemplate;
import in.hocg.eagle.utils.SpelParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Created by hocgin on 2020/4/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class MailManager implements InitializingBean {
    private static final String PREFIX = "[EAGLE]";
    private final MailTemplate template;
    private SpringTemplateEngine templateEngine;

    /**
     * 发送重置密码邮件
     *
     * @param nickname         昵称
     * @param email            邮箱
     * @param resetPasswordUrl 重置密码链接
     */
    public void sendResetPasswordMail(@NotNull String nickname,
                                      @NotNull String email,
                                      @NotNull String resetPasswordUrl) {
        final Mail type = Mail.RESET_PASSWORD_MAIL;
        final Map<String, Object> vars = type.getDefaultVars();
        vars.put("RESET_PASSWORD_URL", resetPasswordUrl);
        vars.put("NICKNAME", nickname);
        this.sendWithTemplate(email, type.getTitle(), type.getTemplate(), vars);
    }

    /**
     * 发送邮件
     */
    private void send(@NotNull String email, @NotNull String title,
                      @NotNull String templateText, Map<String, Object> vars) {
        if (Objects.nonNull(vars)) {
            StandardEvaluationContext context = new StandardEvaluationContext();
            context.setVariables(vars);
            SpelParser.parse(templateText, context);
        }
        template.send(Lists.newArrayList(email), title(title), templateText);
    }


    private void sendWithTemplate(@NotNull String email, @NotNull String title,
                                  @NotNull String templateName, Map<String, Object> vars) {
        template.send(Lists.newArrayList(email), title(title), thymeleafTemplate(templateName, vars));
    }

    /**
     * 解析 thymeleaf 语法
     */
    private String thymeleafTemplate(String templateName, Map<String, Object> vars) {
        Context ctx = new Context();
        Optional.ofNullable(vars).ifPresent(ctx::setVariables);
        return templateEngine.process(templateName, ctx);
    }

    /**
     * 处理标题
     *
     * @param title
     * @return
     */
    private String title(String title) {
        return String.format("%s %s", PREFIX, title);
    }

    @Override
    public void afterPropertiesSet() {
        Set<ITemplateResolver> resolvers = Sets.newHashSet();

        // 邮件模版解析
        ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
        emailTemplateResolver.setPrefix("/template/mail/");
        emailTemplateResolver.setSuffix(".html");
        emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
        emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        emailTemplateResolver.setOrder(1);
        resolvers.add(emailTemplateResolver);
        templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolvers(resolvers);
    }
}
