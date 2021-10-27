package com.pgs.praktyki.rzeintern1021.services;

import com.pgs.praktyki.rzeintern1021.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    JavaMailSender javaMailSender;

    @Mock
    UserService userService;

    @InjectMocks
    EmailService emailService;

    @Test
    public void sendActivationEmail_should_send_this_email() {
        User user = new User();
        user.setEmail("email");
        user.setUsername("name");
        when(userService.getUserEntityByUUID("uuid3")).thenReturn(user);
        emailService.sendActivationEmail("uuid3/activationlink");

        ArgumentCaptor<SimpleMailMessage> emailCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender, times(1)).send(emailCaptor.capture());

        List<SimpleMailMessage> actualList = emailCaptor.getAllValues();
        assertTrue(actualList.size() == 1);
    }
}