package ro.tuc.ds2020.security;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class handling entry point configurations
 * @author Zaharia Tudorita
 */
@Component
public class RestAuthenticationEntryPointConfig implements AuthenticationEntryPoint {

    /**
     * Method for handling errors due to unauthorized request methods
     * @param request the one being unauthorized
     * @param response for being unauthorized
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException.getMessage());

    }

}



