package com.pgs.praktyki.rzeintern1021.aws.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class AwsTestConfiguration {
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretAccessKey;

    @Bean
    public AWSCredentials createCredentials() {
        return new BasicAWSCredentials(accessKey, secretAccessKey);
    }

    @Primary
    @Bean
    public AmazonSQSAsync sqsAsyncClient() {
        return AmazonSQSAsyncClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(this.createCredentials()))
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                "http://localhost:4566/000000000000/test-queue",
                "us-east-2"))
            .build();
    }

    @Primary
    @Bean
    public AmazonS3 s3Client() {
        return AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(this.createCredentials()))
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566/", "us-east-2"))
            .withPathStyleAccessEnabled(true)
            .build();
    }
}