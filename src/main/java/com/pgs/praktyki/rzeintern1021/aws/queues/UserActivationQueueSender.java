package com.pgs.praktyki.rzeintern1021.aws.queues;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserActivationQueueSender {

    private final AmazonSQSAsync amazonSQSAsync;

    public UserActivationQueueSender(AmazonSQSAsync amazonSQSAsync) {
        this.amazonSQSAsync = amazonSQSAsync;
    }

    @Value("${cloud.aws.end-point.uri}")
    private String sqsUrl;

    public void sendToQueue(final String message) {
        if (amazonSQSAsync.sendMessage(new SendMessageRequest(sqsUrl, message)) == null) {
            throw new RuntimeException("SQS message problem");
        }
    }


}