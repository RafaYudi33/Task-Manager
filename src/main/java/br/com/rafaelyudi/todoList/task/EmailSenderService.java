package br.com.rafaelyudi.todoList.task;

import br.com.rafaelyudi.todoList.User.UserModel;

import java.util.UUID;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.Data;
import br.com.rafaelyudi.todoList.EmailSender.SesEmailSender;
import br.com.rafaelyudi.todoList.User.IUserRepository;

@Component
@EnableScheduling
public class EmailSenderService {
    
    @Autowired
    private TaskService taskService; 
    
    @Autowired
    private SesEmailSender sesEmailSender; 

    @Autowired
    private IUserRepository userRepository;


    @Scheduled(cron = "0 0 18 * * ?")
    public void taskEmailSenderService(){
        var tasks = this.taskService.findTasksCloseEnd();
        

        for(TaskModel task : tasks){
            var userOptional = this.userRepository.findById(task.getIdUser());
            
            if (userOptional.isPresent()) {
                UserModel user = userOptional.get();
                var email = user.getEmail();

                this.sesEmailSender.sendEmail(email, "Tarefa Perto do Fim"
                ,"Sua tarefa esta há menos de 24 horas de acabar");
            }
            
        }
    }
}
