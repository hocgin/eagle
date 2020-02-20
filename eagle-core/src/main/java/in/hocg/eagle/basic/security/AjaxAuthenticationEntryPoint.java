package in.hocg.eagle.basic.security;

import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.basic.result.ResultCode;
import in.hocg.eagle.utils.ResponseUtility;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hocgin on 2020/1/9.
 * email: hocgin@gmail.com
 * 匿名访问被拒绝
 *
 * @author hocgin
 */
public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        final ResultCode resultCode = ResultCode.AUTHENTICATION_ERROR;
        final Result<Object> result = Result.error(resultCode.getCode(), "未登录");
        
        ResponseUtility.setUtf8(response);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try (final PrintWriter writer = response.getWriter()) {
            writer.write(result.json());
        }
    }
}
