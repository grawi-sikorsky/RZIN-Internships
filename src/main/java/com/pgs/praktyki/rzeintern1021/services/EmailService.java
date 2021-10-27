package com.pgs.praktyki.rzeintern1021.services;

import com.pgs.praktyki.rzeintern1021.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final UserService userService;

    @Value("${app.url}")
    private String appURL;

    public EmailService(JavaMailSender javaMailSender, UserService userService) {
        this.javaMailSender = javaMailSender;
        this.userService = userService;
    }

    /**
     * Receives UUID and activation link from SQS!
     *
     * @param sqsIncomingJson
     */
    public void sendActivationEmail(final String sqsIncomingJson) {
        String[] split = sqsIncomingJson.split("/");
        String uuid = split[0];
        String activationCode = split[1];

        javaMailSender.send(this.prepareMessage(activationCode, userService.getUserEntityByUUID(uuid)));
    }

    public SimpleMailMessage prepareMessage(final String activationCode, User user) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());

        simpleMailMessage.setSubject(user.getUsername() + ", aktywuj konto");
        simpleMailMessage.setText("Witoj " + user.getFirstname() + "! \n" +
            "Aby korzystać z dobrodziejstw tejże cudownej aplikacji, \n" +
            "posyłamy Ci link aktywujacy, w który niezwłocznie MUSISZ kliknąć, \n" +
            "w przeciwnym pozostaniesz waść nieaktywny do odwołania. \n\n" +
            "Link aktywacyjny(http patch!): " + appURL + "/user/" + user.getUuid() + "/activate/" + activationCode
        );
        return simpleMailMessage;
    }
}