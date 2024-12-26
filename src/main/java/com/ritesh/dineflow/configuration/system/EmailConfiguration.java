package com.ritesh.dineflow.configuration.system;

import com.ritesh.dineflow.configuration.dto.EmailPropertiesDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class EmailConfiguration {

	@Bean
	public JavaMailSender setMailSender(EmailPropertiesDTO emailProperties) {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(emailProperties.getHost());
		mailSender.setPort(emailProperties.getPort());
		mailSender.setUsername(emailProperties.getUserName());
		mailSender.setPassword(emailProperties.getPassword());
		mailSender.setProtocol(emailProperties.getTransferProtocol());

		Properties properties = mailSender.getJavaMailProperties();
		properties.put("mail.transport.protocol", emailProperties.getTransferProtocol());
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enable", emailProperties.getEnableStartTls());

		return mailSender;
	}
}
