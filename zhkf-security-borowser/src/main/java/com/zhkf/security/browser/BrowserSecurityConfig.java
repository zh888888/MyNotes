package com.zhkf.security.browser;

import com.zhkf.security.core.properties.SecurityProperties;
import com.zhkf.security.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


import javax.sql.DataSource;

/***
 * WebSecurityConfigurerAdapter Web安全应用的适配器类
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler zhkfAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler zhkfAuthenticationFailureHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
       tokenRepository.setDataSource(dataSource);
     // tokenRepository.setCreateTableOnStartup(true);       //启动的时候就创建表
        return tokenRepository;
    }
  @Override
  protected void configure(HttpSecurity http) throws Exception {
      ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
      validateCodeFilter.setAuthenticationFailureHandler(zhkfAuthenticationFailureHandler);
      validateCodeFilter.setSecurityProperties(securityProperties);
      validateCodeFilter.afterPropertiesSet();
        //表单登录
      http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
              .formLogin()
              .loginPage("/authentication/require")
              .loginProcessingUrl("/authentication/form")
              //表单登录成功后，就会进入到该方法里
              .successHandler(zhkfAuthenticationSuccessHandler)
              //表单登录失败，进入到该方法里
              .failureHandler(zhkfAuthenticationFailureHandler)
              .and()
              .rememberMe()
              .tokenRepository(persistentTokenRepository())
              //过期的秒数
              .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
              .userDetailsService(userDetailsService)
              //Basic认证方式
//		http.httpBasic()
              .and()
              //表示下面这些都是要经过授权的配置
              .authorizeRequests()
              //当访问这个路径时，不需要身份认证
              .antMatchers("/authentication/require",
                      securityProperties.getBrowser().getLoginPage(),"/code/*").permitAll()
              //任何请求
              .anyRequest()
              //都需要身份认证
              .authenticated()
              .and()
              //将自带的跨域请求禁调
              .csrf().disable();

  }


}
