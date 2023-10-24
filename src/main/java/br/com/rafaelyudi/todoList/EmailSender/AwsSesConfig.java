package br.com.rafaelyudi.todoList.EmailSender;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

@Configuration
public class AwsSesConfig {
    
    String accessKey = "AKIA2FCGGSOA3UFAGKXX";
    String secretKey = "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";

    BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

    @Bean
    public AmazonSimpleEmailService AmazonSimpleEmailService(){
        return AmazonSimpleEmailServiceClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).withRegion(Regions.US_EAST_1).build(); 
    }
}
