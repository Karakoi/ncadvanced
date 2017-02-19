package com.overseer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:email.properties")
public class EmailConfig {

    @Value("${mail.host}")
    private String mailHost;
    @Value("${mail.port}")
    private String mailPort;
    @Value("${mail.email}")
    private String senderEmail;
    @Value("${mail.password}")
    private String senderPassword;

    /**
     * @return configured JavaMailSender
     */
    @Bean
    public JavaMailSenderImpl mailSender() {
        final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(this.mailHost);
        javaMailSender.setPort(Integer.valueOf(this.mailPort));
        javaMailSender.setUsername(this.senderEmail);
        javaMailSender.setPassword(this.senderPassword);

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.sendpartial", "true");

        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }
}
