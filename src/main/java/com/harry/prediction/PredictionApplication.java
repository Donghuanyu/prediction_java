package com.harry.prediction;

import com.harry.prediction.util.OkHttpUtil;
import okhttp3.OkHttpClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.harry.prediction.mapper")
public class PredictionApplication {

    public static void main(String[] args) {
        SpringApplication.run(PredictionApplication.class, args);
    }

    @Bean(name = "okHttp")
    public OkHttpClient okHttp() {
        return OkHttpUtil.okHttpClientHttp();
    }

    @Bean(name = "okHttps")
    public OkHttpClient okHttps() {
        return OkHttpUtil.okHttpClientHttps();
    }

}
