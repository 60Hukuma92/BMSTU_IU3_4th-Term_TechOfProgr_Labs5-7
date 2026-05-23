package com.test.magicalhaven.server.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.*
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages = ["com.test.magicalhaven.server", "org.springdoc"])
@PropertySource("classpath:application.properties")
open class AppConfig : WebMvcConfigurer {

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        converters.add(MappingJackson2HttpMessageConverter(jacksonObjectMapper()))
    }

    companion object {
        @JvmStatic
        @Bean
        fun propertySourcesPlaceholderConfigurer() = PropertySourcesPlaceholderConfigurer()
    }
    
    @Bean
    open fun springDocConfig(): org.springdoc.core.properties.SpringDocConfigProperties {
        return org.springdoc.core.properties.SpringDocConfigProperties()
    }
}
