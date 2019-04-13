package com.golomt.example.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Common Properties @author Tushig
 */

@Component
@PropertySource("classpath:common.properties")
public class CommonProperties {
}