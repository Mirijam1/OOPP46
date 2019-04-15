package nl.tudelft.gogreen.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class MailTemplates {
    /**
     * registration Confirmation Mail Template Message.
     * @return MailMessage.
     */
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

    /**
     *  Registration Completion Mail Template Message.
     * @return MailMessage.
     */
    @Bean
    public SimpleMailMessage registrationComplete() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setSubject("Welcome to GoGreen");
        mailMessage.setText("Hey %s!<br><br>Your registration was just completed, you can now open the "
                + "application and start saving the earth. <br>Welcome to GoGreen!"
                + "<br><br>Yours securely, <br>Team GoGreen");

        return mailMessage;
    }

    /**
     * Creates a SimpleMailMessage to inform the user that their account is secure.
     * @return the predetermined informative message.
     */
    @Bean
    public SimpleMailMessage twoFactorAuthenticationEnabled() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setSubject("Your account is now secure");
        mailMessage.setText("Hey %s!<br><br>Two-factor authentication was just enabled on your account! "
                + "<br>Good job on securing your account, you can now save the earth without having to worry about "
                + "your privacy."
                + "<br><br>Yours securely, <br>Team GoGreen");

        return mailMessage;
    }

    /**
     * Creates a new SimpleMailMessage that informs the user that two-factor authentication.
     * Has been disabled for their account.
     * @return the message
     */
    @Bean
    public SimpleMailMessage twoFactorAuthenticationDisabled() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setSubject("Important security notice");
        mailMessage.setText("Hey %s!<br><br>Two-factor authentication was just disabled on your account!"
                + "<br>For your safety, we highly recommend using two-factor authentication. Please consider enabling "
                + "it again!"
                + "<br><br>Yours securely, <br>Team GoGreen");

        return mailMessage;
    }
}
