package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.config.MailTemplates;
import nl.tudelft.gogreen.server.exceptions.ServiceUnavailable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

@Service
public class MailService implements IMailService {
    private final Logger logger = LoggerFactory.getLogger(MailService.class);
    private final JavaMailSender mailSender;
    private final MailTemplates mailTemplates;
    private boolean mailEnabled;

    @Autowired
    private Environment environment;

    @Autowired
    /*
     * If you IDE complains about beans (mailSender) not being present you can ignore it.
     * The beans ARE PRESENT, just not recognized by Intellij (maybe other IDEs)
     */
    public MailService(JavaMailSender mailSender, MailTemplates mailTemplates) {
        this.mailSender = mailSender;
        this.mailTemplates = mailTemplates;
        this.mailEnabled = false;
    }

    @Override
    public boolean mailAvailable() {
        return this.mailEnabled;
    }

    @Override
    @Scheduled(fixedRate = 1000 * 60 * 30) // Checks every 30 mins
    public void checkConnection() {
        if (this.mailAvailable() || Arrays.asList(environment.getActiveProfiles()).contains("tests")) {
            return;
        }

        logger.info("Checking mail service availability");

        try {
            this.sendMessage("noreply.gogreen@gmail.com", "Startup", "Verifying mail availability");
        } catch (ServiceUnavailable unavailable) {
            logger.warn("Disabling mail for this instance!");
            this.mailEnabled = false;
            return;
        }

        logger.info("Enabling mail for this instance!");
        this.mailEnabled = true;
    }


    @Override
    public void sendMessage(String target, String subject, String message) {
        if (target == null) {
            return;
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            mimeMessage.setContent(message, "text/html");
            helper.setTo(target);
            helper.setSubject(subject);
            helper.setFrom("GoGreen");
            mailSender.send(mimeMessage);
        } catch (MessagingException | MailSendException e) {
            logger.error("Could not send mail, cause: " + e.getCause());
            throw new ServiceUnavailable();
        }
    }

    @Override
    @Async
    public void sendRegistrationMessage(String target, String name, String code, String externalId) {
        SimpleMailMessage unformattedMail = mailTemplates.registerConfirmationTemplate();
        String mail = String.format(unformattedMail.getText(), name, code, externalId);

        this.sendMessage(target, unformattedMail.getSubject(), mail);
    }

    @Override
    @Async
    public void sendRegistrationCompleteMessage(String target, String name) {
        SimpleMailMessage unformattedMail = mailTemplates.registrationComplete();
        String mail = String.format(unformattedMail.getText(), name);

        this.sendMessage(target, unformattedMail.getSubject(), mail);
    }
}
