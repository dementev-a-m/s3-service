package ru.dementev.s3service.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Configuration {
    @Value("${app.s3.region:us-east-1}")
    public  String awsRegion;
    @Value("${app.s3.endpoint:http://localhost:4566}")
    public  String s3Endpoint;
    @Value("${app.s3.accessKey:foo}")
    private String accessKey;
    @Value("${app.s3.secretKey:bar}")
    private String secretKey;


    @Bean
    public AmazonS3 amazonS3() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AwsClientBuilder.EndpointConfiguration config =
                new AwsClientBuilder.EndpointConfiguration(s3Endpoint, awsRegion);
        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard();
        builder.withEndpointConfiguration(config);
        builder.withPathStyleAccessEnabled(true);
        builder.withCredentials(new AWSStaticCredentialsProvider(credentials));
        return builder.build();
    }
}
