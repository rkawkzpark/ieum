package com.ieum.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 1. @OpenAPIDefinition 어노테이션 추가
@OpenAPIDefinition(
        info = @Info(
                title = "'이음' API 명세서",
                description = "프로젝트 '이음'의 API 기능들을 설명하는 문서입니다.",
                version = "v1.0.0"
        )
)

@Configuration
public class SwaggerConfig {

        // 2. OpenAPI Bean 설정 변경
        @Bean
        public OpenAPI openAPI() {
                // Security Scheme 이름
                String jwtSchemeName = "bearerAuth";

                // API 요청 헤더에 인증 정보 포함
                SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

                // Security Schemes 설정
                Components components = new Components()
                        .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                                .name(jwtSchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"));

                return new OpenAPI()
                        .components(components)
                        .addSecurityItem(securityRequirement);
        }
}