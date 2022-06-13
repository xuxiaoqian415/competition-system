package zust.competition.sys.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//扩展使用SpringMVC
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Value("${upload.location.pics}")
    private String uploadPicsPath;
    @Value("${upload.route.pics}")
    private String uploadPicsRoute;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //注册配置类，使用addResourceHandlers方法，将本地路径uploadPicsPath映射到uploadPicsRoute路由上。
        registry.addResourceHandler(uploadPicsRoute).addResourceLocations("file:" + uploadPicsPath);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
