package ru.successfulege.analytics;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication @EnableScheduling
public class TildaAnalyticsApplication {
 public static void main(String[] args) { SpringApplication.run(TildaAnalyticsApplication.class, args); }
}
