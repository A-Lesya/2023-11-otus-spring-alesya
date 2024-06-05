package com.bercut.autotests.tools.jenkins_job_orchestrator.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity(
        securedEnabled = true
)
@Configuration
public class MethodSecurityConfiguration {
}
