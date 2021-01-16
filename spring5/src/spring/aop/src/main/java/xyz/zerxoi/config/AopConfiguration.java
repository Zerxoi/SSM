package xyz.zerxoi.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"xyz.zerxoi"})
@EnableAspectJAutoProxy
public class AopConfiguration {
    
}
