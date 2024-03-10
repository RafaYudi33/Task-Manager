package br.com.rafaelyudi.todoList.Task;

import br.com.rafaelyudi.todoList.User.UserModel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
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


   // @Scheduled(cron = "0 0 18 * * ?")
    @Scheduled(fixedRate = 10000)
    public void taskEmailSenderService(){

        var tasks = this.taskService.findTasksCloseStart();

        
        for(TaskModel task :  tasks){

            var userOptional =  this.userRepository.findById(task.getIdUser());
            if (userOptional.isPresent()) {
                UserModel user = userOptional.get();
                this.sesEmailSender.sendEmail(user.getEmail(), "Tarefa perto do ínício"
                ,"Não se esqueça! "+task.getTitle()+" começa em menos de 1 hora!");
            }
            
        }
    }
}
