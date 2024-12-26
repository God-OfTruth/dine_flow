package com.ritesh.dineflow.services;

import com.ritesh.dineflow.dto.EmailInfo;
import jakarta.annotation.Nullable;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.List;

@Service
@Profile("prod")
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateEngine templateEngine;

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailInfo.class);

	@Async("emailTaskExecutor")
	public void sendMail(EmailInfo emailInfo, @Nullable List<File> attachments) {

		emailInfo.getTo().forEach(to -> {

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper;
			try {
				helper = new MimeMessageHelper(message, true, "UTF-8");
				if (attachments != null) {
					for (File attach : attachments) {
						helper.addAttachment(attach.getName(), attach);
					}
				}
				helper.setFrom(emailInfo.getFrom());
				helper.setSubject(emailInfo.getSubject());
				helper.setText(templateEngine.process(emailInfo.getTemplateName(), emailInfo.getContext()),
						emailInfo.getHtml());
				helper.setTo(to);
				mailSender.send(message);
			} catch (MessagingException e) {
				LOGGER.error("Error Sending Message to: {}", to);
			} catch (Exception e) {
				LOGGER.error("Unknown Error Sending Message to: {}", e.getMessage());
			}
		});
	}

	public String getHTML(String templateName, Context context) {
		return templateEngine.process(templateName, context);
	}
}
