package com.pgs.praktyki.rzeintern1021.aws.queues;

import com.pgs.praktyki.rzeintern1021.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;


@Component
public class UserActivationQueueListener {
    private static final Logger logger = LoggerFactory.getLogger(UserActivationQueueListener.class);
    private final EmailService emailService;

    public UserActivationQueueListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @SqsListener(value = "${cloud.aws.end-point.uri}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
    public void sqsListner(final String receivedJson) {
        logger.info(receivedJson);
        emailService.sendActivationEmail(receivedJson);
    }
}
