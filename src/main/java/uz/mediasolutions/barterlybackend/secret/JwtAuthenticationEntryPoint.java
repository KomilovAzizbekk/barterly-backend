package uz.mediasolutions.barterlybackend.secret;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import uz.mediasolutions.barterlybackend.manual.ApiResult;
import uz.mediasolutions.barterlybackend.manual.ErrorData;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Gson gson;

    public JwtAuthenticationEntryPoint(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        ApiResult<ErrorData> errorDataApiResult = ApiResult.error("Not allowed to enter", 403);
        httpServletResponse.getWriter().write(gson.toJson(errorDataApiResult));
        httpServletResponse.setStatus(403);
        httpServletResponse.setContentType("application/json");
    }

}
