package net.doodle;

import net.doodle.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * TODO : 로그인 패스패턴
     */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/board/add","/**")
                .excludePathPatterns("/","/css/**","/*.ico","/error","/login","/logout","/board","/board/*");
    }
}
