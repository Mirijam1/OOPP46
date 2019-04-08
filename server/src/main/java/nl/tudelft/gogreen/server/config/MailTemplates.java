package nl.tudelft.gogreen.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class MailTemplates {
    @Bean
    public SimpleMailMessage registerConfirmationTemplate() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setSubject("Complete your registration");
        mailMessage.setText("Hey %s!<br><br>To complete your registration and start saving the earth please enter "
                + "these numbers into the registration box in the GoGreen application: <br><b>%s</b><br>"
                + "This code will expire in 1 hour. If you accidentally close the application use the following code: "
                + "<i>%s</i><br>If you did not expect this mail, you can ignore it."
                + "<br><br>Yours securely, <br>Team GoGreen");

        return mailMessage;
    }

    @Bean
    public SimpleMailMessage registrationComplete() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setSubject("Welcome to GoGreen!");
        mailMessage.setText("Hey %s!<br><br>Your registration was just completed, you can now open the "
                + "application and start saving the earth. <br>Welcome to GoGreen!"
                + "<br><br>Yours securely, <br>Team GoGreen");

        return mailMessage;
    }
}
