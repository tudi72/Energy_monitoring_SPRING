package ro.tuc.ds2020.security;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

/**
 * Helper class for handling token information,creation,processing, expiration, algorithm
 * @author Zaharia Tudorita
 */
@Slf4j
@Component
public class JWTTokenHelper {


    @Value("${jwt.auth.app}")
    private String appName = "spring-grpc-demo";

    
    @Value("${jwt.auth.secret_key}")
    private String secretKey = "secret";

    @Value("${jwt.auth.expires_in}")
    private int expiresIn;


    public String getAllRolesFromToken(String token){
        String role = null;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            Object ob = claims.get("roles");
            if(ob == null) throw new Exception("hihi");
            role = String.valueOf(ob);
        } 
        catch(NullPointerException ex){
            log.error("[JWTTokenHelper.getAllRoles] : claims.get(roles) = null");
        }
        catch (Exception e) {
            log.error("[JWTTokenHelper.getAllRoles] : " + e.getMessage());
        }
        return role;
    }

    /**
     * Retrieves claims given a token
     * @param token as a string
     * @return
     */
    private Claims getAllClaimsFromToken(String token) {

        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("[JWTTokenHelper.getAllClaims] : "+ e.getMessage());
        }
        return claims;
    }

    /**
     * Method retrieving User's name having a token
     * @param token of the user
     * @return name of user
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * Method for generating a token given an username
     * @param user
     * @return the token
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public String generateAccessToken(User user){

        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 60 minutes
                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign( Algorithm.HMAC256("secret".getBytes()));
    }

    public String generateRefreshToken(User user){
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 30 minutes
                .withIssuedAt(new Date())
                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(Algorithm.HMAC256("secret".getBytes()));
    }

    /**
     * method for validating the token given an user info
     * @param token to be validated
     * @param userDetails the user wishing for validation
     * @return true if token was validated else false
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
                username != null &&
                        username.equals(userDetails.getUsername()) &&
                        !isTokenExpired(token)
        );
    }

    /**
     * Method for checking whether the expiration date is expired
     * @param token to be validated
     * @return true if is expired else false
     */
    public boolean isTokenExpired(String token) {
        Date expireDate=getExpirationDate(token);
        return expireDate.before(new Date());
    }

    /**
     * Method for retrieving the expiration date
     * @param token container of date
     * @return the Date of expiration
     */
    private Date getExpirationDate(String token) {
        Date expireDate;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            expireDate = claims.getExpiration();
        } catch (Exception e) {
            expireDate = null;
        }
        return expireDate;
    }

    /**
     * method for retrieving the date when token was issued
     * @param token container of issue Date
     * @return date
     */
    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    /**
     * Method for getting the token of a request method
     * @param request
     * @return the token
     */
    public String getToken( HttpServletRequest request ) {

        String authHeader = getAuthHeaderFromHeader( request );
        if ( authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    /**
     * Method for getting the header of a request
     * @param request
     * @return authentication header as string
     */
    public String getAuthHeaderFromHeader( HttpServletRequest request ) {
        return request.getHeader("Authorization");
    }
}

