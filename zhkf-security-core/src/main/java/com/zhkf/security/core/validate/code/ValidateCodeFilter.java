package com.zhkf.security.core.validate.code;


import com.zhkf.security.core.properties.SecurityProperties;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();

    private Set<String> urls=new HashSet<>();       //存自定义的url

    private SecurityProperties securityProperties;

    private AntPathMatcher antPathMatcher=new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String[] configUrls=StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(),",");
        if(ArrayUtils.isNotEmpty(configUrls)) {
            for (String configUrl : configUrls) {
                urls.add(configUrl);
            }
        }
        urls.add("/authentication/form");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        boolean action = false;
        for(String url:urls){
            if(antPathMatcher.match(url,httpServletRequest.getRequestURI())){
                action=true;
            }
        }

        if(action){
               // if(StringUtils.equals("/authentication/form", httpServletRequest.getRequestURI())
       // && StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(),"post")){
            try{
                validate(new ServletWebRequest(httpServletRequest));
            }catch (ValidateCodeException e){
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
                return;
            }

        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    //校验r
    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request,
                ValidateCodeController.SESSION_KEY);

        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }

        if(codeInSession == null){
            throw new ValidateCodeException("验证码不存在");
        }

        if(codeInSession.isExpried()){
            sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);

            throw new ValidateCodeException("验证码已过期");
        }

        if(!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public SessionStrategy getSessionStrategy() {
        return sessionStrategy;
    }

    public void setSessionStrategy(SessionStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

}