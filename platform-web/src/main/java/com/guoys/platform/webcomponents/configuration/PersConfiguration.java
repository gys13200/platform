package com.guoys.platform.webcomponents.configuration;

import com.guoys.platform.persistence.helper.MapperHelper;
import com.guoys.platform.persistence.spring.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by gys on 2018/1/3.
 */

@Configuration
public class PersConfiguration {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setMapperHelper(new MapperHelper());
        configurer.setBasePackage("com.guoys.platform.webcomponents.dao");
        return configurer;
    }
}
