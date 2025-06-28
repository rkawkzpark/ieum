package com.ieum.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "'이음' API 명세서",
                description = "프로젝트 '이음'의 API 기능들을 설명하는 문서입니다.",
                version = "v1.0.0"
        )
)
@Configuration
public class SwaggerConfig {
}