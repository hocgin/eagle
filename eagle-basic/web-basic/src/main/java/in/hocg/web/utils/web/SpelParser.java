package in.hocg.web.utils.web;

import lombok.experimental.UtilityClass;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;


/**
 * Created by hocgin on 2018/10/16.
 * email: hocgin@gmail.com
 * - 使用 #{} 包含 SpEL 表达式
 *
 * @author hocgin
 */
@UtilityClass
public class SpelParser {
    private SpelExpressionParser parser;
    private TemplateParserContext templateParserContext;

    static {
        templateParserContext = new TemplateParserContext();
        parser = new SpelExpressionParser(new SpelParserConfiguration(true, true));
    }

    public String parse(String text, EvaluationContext context) {
        Expression expression = parser.parseExpression(text, templateParserContext);
        return expression.getValue(context, String.class);
    }
}
