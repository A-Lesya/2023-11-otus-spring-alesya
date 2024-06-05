package com.bercut.autotests.tools.jenkins_job_orchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.printf("Home page: %s%n", "http://localhost:8080");
    }

}
