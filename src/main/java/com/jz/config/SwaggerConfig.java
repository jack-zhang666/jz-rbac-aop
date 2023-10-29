package com.jz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author jack zhang
 * @version 1.0
 * @description: TODO
 * @date 2023/8/29 16:03
 */

//  * 访问地址:
// * http://127.0.0.1:xxxx/swagger-ui.html#/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.basePackage}")
    String basePackage;
    @Value("${swagger.title}")
    String title;
    @Value("${swagger.description}")
    String description;
    @Value("${swagger.version}")
    String version;
    @Value("${swagger.contact}")
    String contact;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                //服务条款网址
                .version(version)
                .contact(contact)
                .build();
    }
}
