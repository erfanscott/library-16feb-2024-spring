package me.erfan.libraryrest.security.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

    @Component
    public final class MyAuthenticationEntry implements
        AuthenticationEntryPoint {


        @Override
        public void commence(final HttpServletRequest request, final
        HttpServletResponse response, final AuthenticationException
                                     authException) throws IOException {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }

}

