package demo.test.jeann.exercise.utilities.jwt;

import demo.test.jeann.exercise.data.GlobalLogicRepository;
import demo.test.jeann.exercise.utilities.jwt.exceptions.AuthenticationEntryPointHandler;
import demo.test.jeann.exercise.utilities.jwt.exceptions.CustomAccessDeniedHandler;

import demo.test.jeann.exercise.utilities.jwt.exceptions.CustomLogoutHandler;
import demo.test.jeann.exercise.utilities.jwt.exceptions.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RequestMethod;

import static demo.test.jeann.exercise.utilities.AppConstant.API_REST_PROTECTED_DOSIGNOUT;
import static demo.test.jeann.exercise.utilities.AppConstant.API_REST_PROTECTED_FIND_USER_BY_EMAIL;
import static demo.test.jeann.exercise.utilities.AppConstant.API_REST_PROTECTED_GETUSERS;
import static demo.test.jeann.exercise.utilities.AppConstant.API_REST_UNPROTECTED_DOSIGNIN;
import static demo.test.jeann.exercise.utilities.AppConstant.HAS_ROL_ADMIN;
import static demo.test.jeann.exercise.utilities.AppConstant.HAS_ROL_USER;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired JwtTokenProvider jwtTokenProvider;
  @Autowired GlobalLogicRepository globalLogicRepository;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic()
        .disable()
        .csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(API_REST_UNPROTECTED_DOSIGNIN)
        .permitAll()
        .antMatchers(API_REST_PROTECTED_GETUSERS)
        .hasAnyRole(HAS_ROL_USER, HAS_ROL_ADMIN)
        .antMatchers(API_REST_PROTECTED_FIND_USER_BY_EMAIL)
        .hasAnyRole(HAS_ROL_USER, HAS_ROL_ADMIN)
        .anyRequest()
        .authenticated()
        .and()
        .apply(new JwtConfigurer(jwtTokenProvider))
        .and()
        .logout()
        .logoutRequestMatcher(
            new AntPathRequestMatcher(
                API_REST_PROTECTED_DOSIGNOUT, RequestMethod.POST.toString(), true))
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .deleteCookies("SESSION")
        .deleteCookies("JSESSIONID")
        .addLogoutHandler(new CustomLogoutHandler(jwtTokenProvider, globalLogicRepository))
        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(new AuthenticationEntryPointHandler())
        .accessDeniedHandler(new CustomAccessDeniedHandler());
  }
}
