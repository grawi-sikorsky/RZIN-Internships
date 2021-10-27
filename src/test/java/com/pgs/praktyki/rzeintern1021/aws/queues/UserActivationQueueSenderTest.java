package com.pgs.praktyki.rzeintern1021.aws.queues;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserActivationQueueSenderTest {

    @Mock
    SendMessageResult sendMessageResult;

    @Mock
    AmazonSQSAsync amazonSQSAsync;

    @InjectMocks
    UserActivationQueueSender userActivationQueueSender;

    @Test
    public void sendToQueue_should_add_to_queue() {
        when(amazonSQSAsync.sendMessage(any())).thenReturn(sendMessageResult);
        userActivationQueueSender.sendToQueue("messageToQueue");

        ArgumentCaptor<SendMessageRequest> messageRequestCaptor = ArgumentCaptor.forClass(SendMessageRequest.class);
        verify(amazonSQSAsync, times(1)).sendMessage(messageRequestCaptor.capture());

        List<SendMessageRequest> argumentList = messageRequestCaptor.getAllValues();
        assertTrue(argumentList.size() == 1);
        assertEquals(argumentList.get(0).getMessageBody(), "messageToQueue");
    }
}