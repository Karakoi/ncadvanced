package com.overseer.auth;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * REST authentication Controller.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @org.springframework.beans.factory.annotation.Value("${app.cookie.duration}")
    private int cookieMaxAge;
    private final AuthenticationManager authenticationManager;
    private final TokenHandler tokenHandler;
    private final SecurityContextService securityContextService;

    @Autowired
    AuthenticationController(AuthenticationManager authenticationManager,
                             TokenHandler tokenHandler,
                             SecurityContextService securityContextService) {
        this.authenticationManager = authenticationManager;
        this.tokenHandler = tokenHandler;
        this.securityContextService = securityContextService;
    }

    /**
     * @param params Encapsulates user login and password.
     * @param response Object that contains the response the servlet sends to the client.
     * @return String tooltip to check the cookies.
     * @throws AuthenticationException if Authentication failed.
     */
    @RequestMapping(method = RequestMethod.POST)
    public AuthResponse login(@RequestBody AuthParams params, HttpServletResponse response) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken LOGINTOKEN = params.toAuthenticationToken();
        final Authentication AUTHENTICATION = authenticationManager.authenticate(LOGINTOKEN);
        SecurityContextHolder.getContext().setAuthentication(AUTHENTICATION);
        return new AuthResponse(tokenHandler.createTokenForUser(securityContextService.currentUser()));
    }

    @RequestMapping(method = RequestMethod.GET)
    public String test() {
        return "Secured Information";
    }

    /**
     * Encapsulates user login and password.
     */
    @Value
    private static final class AuthParams {
        private final String email;
        private final String password;

        UsernamePasswordAuthenticationToken toAuthenticationToken() {
            return new UsernamePasswordAuthenticationToken(email, password);
        }
    }

    @SuppressWarnings("PMD.UnusedPrivateField")
    @Value
    private static final class AuthResponse {
        private final String token;
    }
}