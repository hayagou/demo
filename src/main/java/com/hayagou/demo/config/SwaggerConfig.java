package com.hayagou.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig{
    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(swaggerInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.hayagou.demo.controller")) // Controller내용을 읽어 mapping 된 resoure들을 문서화 시킴
                .paths(PathSelectors.any()) // PathSelectors.ant("/v1/**") : v1으로 시작하는 resource들만 문서화 시킴
                .build()
                .useDefaultResponseMessages(false); // 기본으로 세팅되는 200,401,403,404 메시지를 표시 하지 않음
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder().title("Spring Boot RestAPI Documentation")
                .description("서버 API 연동 문서입니다.\n" +
                        "개선목록 \n " +
                        "1. JPA 성능 최적화 \n " +
                        "2. 이미지 서버 구축후 게시물에 이미지 경로 추가 \n " +
                        "3. 딥러닝서버 통신부분 \n " +
                        "4. 회원 비밀번호 변경할때 이메일 인증 추가")
                .license("hayagou").licenseUrl("http://hayagou.github.io").version("1").build();
    }
}