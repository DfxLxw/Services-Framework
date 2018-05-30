package com.example.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Swgger {
  @Bean
  public Docket creatRestApi(){
      return new Docket(DocumentationType.SWAGGER_2)
              .apiInfo(apiInfo())
              .select()
              .apis(RequestHandlerSelectors.basePackage("com.example.web.Controller"))
              .paths(PathSelectors.any())
              .build();
  }
  private ApiInfo apiInfo(){
      return new ApiInfoBuilder()
              .title("Sylar使用Swagger2构建RESTful APIs")
              .description("随时关注我的github")
              .termsOfServiceUrl("等待更新")
              .contact("刘")
              .version("0.1")
              .build();
  }

}
