package xyz.zerxoi.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration // 作为配置类，替代 XML 配置文件
@ComponentScan(basePackages = {"xyz.zerxoi"})
// 等价于 <context:component-scan base-package="xyz.zerxoi"></context:component-scan>
public class SpringConfiguration {
    
}