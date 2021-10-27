package com.pgs.praktyki.rzeintern1021.aws.queues;

import com.pgs.praktyki.rzeintern1021.services.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserActivationQueueListenerTest {

    @Mock
    EmailService emailService;

    @InjectMocks
    UserActivationQueueListener userActivationQueueListener;

    @Test
    public void sqsListner_should_invoke_emailservice_sendemail() {
        String receivedJson = "";
        userActivationQueueListener.sqsListner(receivedJson);
        verify(emailService, times(1)).sendActivationEmail(receivedJson);
    }
}