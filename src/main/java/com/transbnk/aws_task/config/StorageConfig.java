package com.transbnk.aws_task.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class StorageConfig {

    @Value("${spring.aws.accessKey}")
    private String access_key;

    @Value("${spring.aws.secretKey}")
    private String secret_key;

    @Bean
    AmazonS3 amazonS3Builder() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(access_key, secret_key);// creating creditials
        return AmazonS3ClientBuilder.standard()// creating AmazonS3 client
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))// providing static credentials
                .withRegion(Regions.US_EAST_1)// adding region specific to the aws account
                .build();
    }

}
