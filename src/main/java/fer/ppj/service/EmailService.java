package fer.ppj.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;
    private String subject;
    private String acceptMessage;
    private String rejectMessage;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
        this.subject = "[KAMP MLADE NADE] prijava za sudjelovanje";
        this.acceptMessage = "Primljeni ste u kamp slijedit sljedeći link: ";
        this.rejectMessage = "Nažalost niste primljeni u kamp";
    }

    public void accept(String toEmail, String nickHash) {
        var mailMessage = new SimpleMailMessage();

        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(createAcceptMessage(nickHash));

        javaMailSender.send(mailMessage);
    }

    public void reject(String toEmail) {
        var mailMessage = new SimpleMailMessage();

        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(rejectMessage);

        javaMailSender.send(mailMessage);
    }

    private String createAcceptMessage(String nickHash) {
        return acceptMessage + "https://kamp-mlade-nade-progi.herokuapp.com/initial?nickHash=" + nickHash;
    }
}
