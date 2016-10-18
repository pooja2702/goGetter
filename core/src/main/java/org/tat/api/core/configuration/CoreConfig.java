package org.tat.api.core.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"org.tat.api.core.interceptor","org.tat.api.core.processors"})
public class CoreConfig {

}
