package com.ieum.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "스터디 매칭 플랫폼 '이음' API",
                description = """
                        ## 스터디 매칭 플랫폼 '이음' API 명세서
                        
                        '이음' 프로젝트의 공식 API 문서입니다.
                        
                        - **주요 기능:** 사용자 인증, 프로필 관리, 스터디 그룹 관리 등
                        - **문의:** 아래 연락처로 문의 바랍니다.
                        """,
                version = "1.0.0",
                contact = @Contact(
                        name = "박현민",
                        email = "hungrygamja0106@gmail.com",
                        url = "https://github.com/rkawkzpark/ieum"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                )
        )
)
@Configuration
public class SwaggerConfig {

        @Bean
        public OpenAPI openAPI() {
                // 1. SecurityScheme 설정
                SecurityScheme securityScheme = new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization");

                // 2. SecurityRequirement 설정
                SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

                // 3. OpenAPI 객체에 반영
                return new OpenAPI()
                        .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                        .security(java.util.Collections.singletonList(securityRequirement));
        }
}