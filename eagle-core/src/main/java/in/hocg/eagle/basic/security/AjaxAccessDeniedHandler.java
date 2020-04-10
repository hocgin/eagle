package in.hocg.eagle.basic.security;

import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.basic.result.ResultCode;
import in.hocg.eagle.utils.web.ResponseUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hocgin on 2020/1/9.
 * email: hocgin@gmail.com
 * 登录后，访问被拒绝
 *
 * @author hocgin
 */
public class AjaxAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        final ResultCode resultCode = ResultCode.ACCESS_DENIED_ERROR;
        final Result<Object> result = Result.error(resultCode.getCode(), "无权限");

        ResponseUtils.setUtf8(response);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try (final PrintWriter writer = response.getWriter()) {
            writer.write(result.json());
        }
    }
}
