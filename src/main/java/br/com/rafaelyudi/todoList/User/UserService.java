package br.com.rafaelyudi.todoList.User;


import br.com.rafaelyudi.todoList.Errors.UserAlreadyExistsException;
import br.com.rafaelyudi.todoList.Mapper.ModelMapperConverter;
import br.com.rafaelyudi.todoList.Task.TaskController;
import br.com.rafaelyudi.todoList.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserService {

     @Autowired
     private IUserRepository userRepository;

     @Autowired 
     private Utils utils; 
     

     public UserDTO userCreate(UserDTO data) {

          var verifyUserAlreadyExists = this.userRepository.findByUsername(data.getUsername());

          if (verifyUserAlreadyExists != null) {
               throw new UserAlreadyExistsException("Esse nome de usuário ja existe!");
          }

          var passCrypt = utils.passCript(data.getPassword());
          var userModel = ModelMapperConverter.parseObject(data, UserModel.class);
          userModel.setPassword(passCrypt);
          
          
          var userDto = ModelMapperConverter.parseObject(this.userRepository.save(userModel), UserDTO.class);
          userDto.add(linkTo(methodOn(TaskController.class).create(null, null)).withRel("Criar sua primeira tarefa").withType("POST"));
          
          return userDto;
     }


}
