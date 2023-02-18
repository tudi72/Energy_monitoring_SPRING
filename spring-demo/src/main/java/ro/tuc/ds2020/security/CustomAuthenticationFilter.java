package ro.tuc.ds2020.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;



/**
 * Class capable for handling the authentication by using username and password
 */
@Slf4j
 public class CustomAuthenticationFilter  extends UsernamePasswordAuthenticationFilter {


    @Value("${jwt.auth.secret_key}")
    private String secretKey = "secret";




    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter (AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    /**
     * Method for handling an authentication attempt by extracting its username and password
     * @param request having as parameters username and password
     * @param response
     * @return Authentication object
     * @throws AuthenticationException if the authentication was faulty
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("[AuthenticationFilter.attempt] : User with username is: " + username + ", password is: " + password);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * Method for handling a successful authentication
     * It encodes the password and creates an access token and a refresh token for the user
     * Those tokens will be written in the response output stream
     * @param request the method successfully authenticated
     * @param response containing the output stream
     * @param chain
     * @param authentication object containing user parameters
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        log.info("[AuthenticationFilter.successfulAuthentication] : role = " + user.getAuthorities().toArray()[0] + " , authentication = "+ SecurityContextHolder.getContext().getAuthentication().getName());
        log.info("[main] : find user admin " + secretKey);
        // creation of access and refresh token
        // refresh token used only when access token expires to avoid logging
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 60 minutes
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        Map<String,String> tokens = new HashMap<>();
        tokens.put("access_token",access_token);
        tokens.put("role", user.getAuthorities().toArray()[0].toString());

        response.setContentType(APPLICATION_JSON_VALUE);
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers","content-type, x-gwt-module-base, x-gwt-permutation, clientid, longpush");

        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    /**
     * Method for handling unsuccessful authentication
     * @param request having the method
     * @param response what to write in the output stream
     * @param failed the reason it failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //TODO: if the user didn't authenticate well, we could impose a time limit until he can login again etc.
        log.info("User authentication error-prone");
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
