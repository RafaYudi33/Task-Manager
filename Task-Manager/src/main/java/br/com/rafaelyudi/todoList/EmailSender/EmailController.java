package br.com.rafaelyudi.todoList.EmailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import br.com.rafaelyudi.todoList.Errors.EmailServiceException;

@RestController
@RequestMapping("/email")
public class EmailController {
    
    @Autowired
    private SesEmailSender emailSender;
    
    @PostMapping("")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request){

        try{
            this.emailSender.sendEmail(request.to(), request.subject(), request.body());
            return ResponseEntity.ok().body("Email send successfully"); 
        }catch(EmailServiceException ex){
            return ResponseEntity.badRequest().body("Error while sending email"); 
        }

    }

}
