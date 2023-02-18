package ro.tuc.ds2020.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

/**
 * Class for handling all the security configurations in a web app
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Method for creating an Authentication manager
     * @return AuthenticationManager object
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    /**
     * Method for handling the authentication based on userdetails and password encoder
     * @param auth the object to be configured
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        super.configure(auth);
    }

    /**
     * Method for handling the API configuration
     * This method permits for a specific API only a specific Role to access it if it was authenticated and authorized
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        http.csrf().disable().cors().and().headers().frameOptions().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling();
        http.addFilter(customAuthenticationFilter);
        customAuthenticationFilter.setFilterProcessesUrl("/auth/login");

        // http.authorizeRequests().antMatchers("/auth/login/**").permitAll();
        http.authorizeRequests().antMatchers(GET,"/user/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST,"/user/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/send/**").hasAuthority("ROLE_ADMIN");

        http.authorizeRequests().antMatchers("/admin/**").permitAll();

        http.authorizeRequests().antMatchers("/websocket-app/**").permitAll();
        
        http.authorizeRequests().antMatchers("/client/**").hasAnyAuthority("ROLE_CLIENT");
        http.authorizeRequests().antMatchers("/device/**").hasAnyAuthority("ROLE_ADMIN");

        http.authorizeRequests().anyRequest().authenticated();

        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
