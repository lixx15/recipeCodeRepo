package jp.co.feeps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 全てのエンドポイントにCORSを適用
                .allowedOrigins("http://localhost:3000") // フロントエンドのURL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 許可するHTTPメソッド
                .allowedHeaders("*") // 全てのヘッダーを許可
                .allowCredentials(true); // クッキーや認証情報を許可
    }
}
