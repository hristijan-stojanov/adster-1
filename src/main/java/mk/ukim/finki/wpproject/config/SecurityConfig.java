package mk.ukim.finki.wpproject.config;

import mk.ukim.finki.wpproject.security.oauth2.CustomerOAuth2UserService;
import mk.ukim.finki.wpproject.security.oauth2.OAuthLoginSuccessHandler;
import mk.ukim.finki.wpproject.security.CustomUsernamePasswordAuthenticationProvider;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUsernamePasswordAuthenticationProvider authenticationProvider;
    private final CustomerOAuth2UserService customerOAuth2UserService;
    private final OAuthLoginSuccessHandler oAuthLoginSuccessHandler;

    public SecurityConfig(CustomUsernamePasswordAuthenticationProvider authenticationProvider,
                          CustomerOAuth2UserService customerOAuth2UserService, OAuthLoginSuccessHandler oAuthLoginSuccessHandler) {
        this.authenticationProvider = authenticationProvider;
        this.customerOAuth2UserService = customerOAuth2UserService;
        this.oAuthLoginSuccessHandler = oAuthLoginSuccessHandler;
    }


    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/profile", "/add").hasRole("USER")
                .anyRequest()
                .authenticated()
                .and()

                .formLogin()
                .loginPage("/login").permitAll()
                .failureUrl("/login?error=BadCredentials")
                .defaultSuccessUrl("/profile", true)
                .and()

                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(customerOAuth2UserService)
                .and()

                .successHandler(oAuthLoginSuccessHandler)
                .defaultSuccessUrl("/profile")
                .and()

                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login")
                .and()

                .exceptionHandling().accessDeniedPage("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }
}
