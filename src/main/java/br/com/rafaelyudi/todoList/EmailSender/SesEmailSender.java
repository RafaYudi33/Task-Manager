package br.com.rafaelyudi.todoList.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;

import br.com.rafaelyudi.todoList.Errors.EmailServiceException;



@Service 
public class SesEmailSender {
    

    private final AmazonSimpleEmailService amazonSimpleEmailService; 
    
   
    public SesEmailSender(AmazonSimpleEmailService amazonSimpleEmailService){
        this.amazonSimpleEmailService = amazonSimpleEmailService;
    }

    public void sendEmail(String to, String subject, String body){
        
        SendEmailRequest request = new SendEmailRequest()
        .withSource("yudirafael33@gmail.com")
        .withDestination(new Destination().withToAddresses(to))
        .withMessage(new Message()
            .withSubject(new Content(subject))
            .withBody(new Body().withText(new Content(body)))
        );

        try{
           this.amazonSimpleEmailService.sendEmail(request);
        }catch (AmazonServiceException exception){
            throw new EmailServiceException("Failure wih sending email", exception);
        }
    } 
}
